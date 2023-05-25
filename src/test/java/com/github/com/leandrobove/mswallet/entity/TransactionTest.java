package com.github.com.leandrobove.mswallet.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {

    @Test
    public void shouldCreateTransaction() {
        Client client1 = Client.create("John", "john@j.com");
        Client client2 = Client.create("Jack Doe", "jackdoe@j.com");

        Account account1 = Account.create(client1);
        Account account2 = Account.create(client2);

        client1.addAccount(account1);
        client2.addAccount(account2);

        account1.credit(new BigDecimal(1000.00));
        account2.credit(new BigDecimal(1000.00));

        Transaction transaction = new Transaction(account1, account2, new BigDecimal(500.00));

        assertThat(transaction.getId()).isNotNull();
        assertThat(transaction.getAccountFrom()).isEqualTo(account1);
        assertThat(transaction.getAccountTo()).isEqualTo(account2);
        assertThat(transaction.getAmount()).isEqualTo(new BigDecimal(500.00));
        assertThat(transaction.getCreatedAt()).isNotNull();
        assertThat(account1.getBalance()).isEqualTo(new BigDecimal(500.00));
        assertThat(account2.getBalance()).isEqualTo(new BigDecimal(1500.00));
    }

    @Test
    public void shouldNotCreateTransactionWhenAccountFromIsNull() {
        Client client2 = Client.create("Jack Doe", "jackdoe@j.com");
        Account account2 = Account.create(client2);
        client2.addAccount(account2);
        account2.credit(new BigDecimal(1000.00));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(null, account2, new BigDecimal(500.00));
        });
        assertThat(ex.getMessage()).isEqualTo("accountFrom is required");
    }

    @Test
    public void shouldNotCreateTransactionWhenAccountToIsNull() {
        Client client1 = Client.create("John", "john@j.com");
        Account account1 = Account.create(client1);
        client1.addAccount(account1);
        account1.credit(new BigDecimal(1000.00));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(account1, null, new BigDecimal(500.00));
        });
        assertThat(ex.getMessage()).isEqualTo("accountTo is required");
    }

    @Test
    public void shouldNotCreateTransactionWhenAmountIsEqualToZero() {
        Client client1 = Client.create("John", "john@j.com");
        Client client2 = Client.create("Jack Doe", "jackdoe@j.com");

        Account account1 = Account.create(client1);
        Account account2 = Account.create(client2);

        client1.addAccount(account1);
        client2.addAccount(account2);

        account1.credit(new BigDecimal(1000.00));
        account2.credit(new BigDecimal(1000.00));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(account1, account2, BigDecimal.ZERO);
        });
        assertThat(ex.getMessage()).isEqualTo("amount must be greater than zero");
    }

    @Test
    public void shouldNotCreateTransactionWhenAmountIsNegative() {
        Client client1 = Client.create("John", "john@j.com");
        Client client2 = Client.create("Jack Doe", "jackdoe@j.com");

        Account account1 = Account.create(client1);
        Account account2 = Account.create(client2);

        client1.addAccount(account1);
        client2.addAccount(account2);

        account1.credit(new BigDecimal(1000.00));
        account2.credit(new BigDecimal(1000.00));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(account1, account2, new BigDecimal(-20.00));
        });
        assertThat(ex.getMessage()).isEqualTo("amount must be greater than zero");
    }

    @Test
    public void shouldNotCreateTransactionWhenAccountBalanceIsInsufficient() {
        Client client1 = Client.create("John", "john@j.com");
        Client client2 = Client.create("Jack Doe", "jackdoe@j.com");

        Account account1 = Account.create(client1);
        Account account2 = Account.create(client2);

        client1.addAccount(account1);
        client2.addAccount(account2);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(account1, account2, new BigDecimal(20.00));
        });
        assertThat(ex.getMessage()).isEqualTo("insufficient funds");
    }
}
