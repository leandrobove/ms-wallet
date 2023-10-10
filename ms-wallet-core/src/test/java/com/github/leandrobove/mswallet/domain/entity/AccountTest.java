package com.github.leandrobove.mswallet.domain.entity;

import com.github.leandrobove.mswallet.domain.event.AccountCreatedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

    private static final Client CLIENT = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");

    @Test
    public void shouldCreateNewAccount() {
        Account account = Account.create(CLIENT);

        assertThat(account.getId()).isNotNull();
        assertThat(account.getClient()).isEqualTo(CLIENT);
        assertThat(account.getBalance()).isEqualTo(Money.ZERO);
        assertThat(account.getCreatedAt()).isNotNull();
        assertThat(account.getUpdatedAt()).isNotNull();

        assertThat(account.getDomainEvents().size()).isEqualTo(1);

        AccountCreatedEvent accountCreatedEvent = (AccountCreatedEvent) account.getDomainEvents().get(0);
        assertThat(accountCreatedEvent.getBalance().doubleValue()).isEqualTo(BigDecimal.ZERO.doubleValue());
        assertThat(accountCreatedEvent.getAccountId()).isEqualTo(account.getId().value());
        assertThat(accountCreatedEvent.getClientId()).isEqualTo(account.getClient().getId().value());
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
        assertThat(account.getBalance()).isEqualTo(Money.ZERO);
        account.credit(Money.from(BigDecimal.valueOf(1000.00)));
        assertThat(account.getBalance()).isEqualTo(Money.from(new BigDecimal(1000.00)));
        account.credit(Money.from(BigDecimal.valueOf(500.00)));
        assertThat(account.getBalance()).isEqualTo(Money.from(new BigDecimal(1500.00)));
    }

    @Test
    public void shouldNotCreditWhenAmountIsNull() {
        Account account = Account.create(CLIENT);

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            account.credit(null);
        });
        assertThat(ex.getMessage()).isEqualTo("amount is required");
    }

    @Test
    public void shouldNotCreditWhenAmountIsNegative() {
        Account account = Account.create(CLIENT);

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            account.credit(Money.from(BigDecimal.valueOf(-100.00)));
        });
        assertThat(ex.getMessage()).isEqualTo("amount must be a positive number");
    }

    @Test
    public void shouldNotCreditWhenAmountIsEqualToZero() {
        Account account = Account.create(CLIENT);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            account.credit(Money.from(BigDecimal.valueOf(0.00)));
        });
        assertThat(ex.getMessage()).isEqualTo("amount must be a positive number");
    }

    @Test
    public void shouldNotDebitWhenAmountIsNegative() {
        Account account = Account.create(CLIENT);
        account.credit(Money.from(BigDecimal.valueOf(100.00)));

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            account.debit(Money.from(BigDecimal.valueOf(-100.00)));
        });
        assertThat(ex.getMessage()).isEqualTo("amount must be a positive number");
    }

    @Test
    public void shouldNotDebitWhenAmountIsNull() {
        Account account = Account.create(CLIENT);
        account.credit(Money.from(BigDecimal.valueOf(1000.00)));

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            account.debit(null);
        });
        assertThat(ex.getMessage()).isEqualTo("amount is required");
    }

    @Test
    public void shouldNotDebitWhenInsufficientFundsInBalance() {
        Account account = Account.create(CLIENT);
        account.credit(Money.from(BigDecimal.valueOf(1000.00)));

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            account.debit(Money.from(BigDecimal.valueOf(1000.01)));
        });
        assertThat(ex.getMessage()).isEqualTo("insufficient funds");
    }

    @Test
    public void shouldDebit() {
        Account account = Account.create(CLIENT);
        assertThat(account.getBalance()).isEqualTo(Money.ZERO);
        account.credit(Money.from(BigDecimal.valueOf(1000.00)));
        assertThat(account.getBalance()).isEqualTo(Money.from(new BigDecimal(1000.00)));
        account.debit(Money.from(BigDecimal.valueOf(500.00)));
        assertThat(account.getBalance()).isEqualTo(Money.from(new BigDecimal(500.00)));
        account.debit(Money.from(BigDecimal.valueOf(250.00)));
        assertThat(account.getBalance()).isEqualTo(Money.from(new BigDecimal(250.00)));
        account.debit(Money.from(BigDecimal.valueOf(250.00)));
        assertThat(account.getBalance()).isEqualTo(Money.ZERO);
    }
}
