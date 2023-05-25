package com.github.com.leandrobove.mswallet.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

    private static final Client CLIENT = Client.create("John", "j@j.com");

    @Test
    public void shouldCreateNewAccount() {
        Account account = Account.create(CLIENT);

        assertThat(account.getId()).isNotNull();
        assertThat(account.getClient()).isEqualTo(CLIENT);
        assertThat(account.getBalance()).isEqualTo(BigDecimal.ZERO);
        assertThat(account.getCreatedAt()).isNotNull();
        assertThat(account.getUpdatedAt()).isNotNull();
    }

    @Test
    public void shouldNotCreateNewAccountWhenClientIsNull() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Account account = Account.create(null);
        });
        assertThat(ex.getMessage()).isEqualTo("client is required");
    }

    @Test
    public void shouldCredit() {
        Account account = Account.create(CLIENT);
        assertThat(account.getBalance()).isEqualTo(BigDecimal.ZERO);
        account.credit(new BigDecimal(1000.00));
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(1000.00));
        account.credit(new BigDecimal(500.00));
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(1500.00));
    }

    @Test
    public void shouldNotCreditWhenAmountIsNull() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Account account = Account.create(CLIENT);
            account.credit(null);
        });
        assertThat(ex.getMessage()).isEqualTo("amount is required");
    }

    @Test
    public void shouldNotCreditWhenAmountIsNegative() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Account account = Account.create(CLIENT);
            account.credit(new BigDecimal(-100.00));
        });
        assertThat(ex.getMessage()).isEqualTo("amount must be a positive number");
    }

    @Test
    public void shouldNotCreditWhenAmountIsEqualToZero() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Account account = Account.create(CLIENT);
            account.credit(new BigDecimal(0.00));
        });
        assertThat(ex.getMessage()).isEqualTo("amount must be a positive number");
    }

    @Test
    public void shouldNotDebitWhenAmountIsNegative() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Account account = Account.create(CLIENT);
            account.credit(new BigDecimal(100.00));
            account.debit(new BigDecimal(-100.00));
        });
        assertThat(ex.getMessage()).isEqualTo("amount must be a positive number");
    }

    @Test
    public void shouldNotDebitWhenAmountIsNull() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Account account = Account.create(CLIENT);
            account.credit(new BigDecimal(1000.00));
            account.debit(null);
        });
        assertThat(ex.getMessage()).isEqualTo("amount is required");
    }

    @Test
    public void shouldNotDebitWhenInsufficientFundsInBalance() {
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Account account = Account.create(CLIENT);
            account.credit(new BigDecimal(1000.00));
            account.debit(new BigDecimal(1000.01));
        });
        assertThat(ex.getMessage()).isEqualTo("insufficient funds");
    }

    @Test
    public void shouldDebit() {
        Account account = Account.create(CLIENT);
        assertThat(account.getBalance()).isEqualTo(BigDecimal.ZERO);
        account.credit(new BigDecimal(1000.00));
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(1000.00));
        account.debit(new BigDecimal(500.00));
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(500.00));
        account.debit(new BigDecimal(250.00));
        assertThat(account.getBalance()).isEqualTo(new BigDecimal(250.00));
        account.debit(new BigDecimal(250.00));
        assertThat(account.getBalance()).isEqualTo(BigDecimal.ZERO);
    }
}
