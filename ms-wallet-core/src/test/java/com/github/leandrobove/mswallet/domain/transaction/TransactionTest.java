package com.github.leandrobove.mswallet.domain.transaction;

import com.github.leandrobove.mswallet.domain.Money;
import com.github.leandrobove.mswallet.domain.account.Account;
import com.github.leandrobove.mswallet.domain.client.Client;
import com.github.leandrobove.mswallet.domain.transaction.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {

    @Test
    public void shouldCreateTransaction() {
        Client client1 = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        Client client2 = Client.create("Jack", "Doe", "jackdoe@hotmail.com", "152.163.310-00");

        Account account1 = Account.create(client1);
        Account account2 = Account.create(client2);

        client1.addAccount(account1);
        client2.addAccount(account2);

        account1.credit(Money.from(BigDecimal.valueOf(1000.00)));
        account2.credit(Money.from(BigDecimal.valueOf(1000.00)));

        Transaction transaction = Transaction.transfer(account1, account2, Money.from(BigDecimal.valueOf(500.00)));

        assertThat(transaction.getId()).isNotNull();
        assertThat(transaction.getAccountFrom()).isEqualTo(account1);
        assertThat(transaction.getAccountTo()).isEqualTo(account2);
        assertThat(transaction.getAmount()).isEqualTo(Money.from(new BigDecimal(500.00)));
        assertThat(transaction.getCreatedAt()).isNotNull();
        assertThat(account1.getBalance()).isEqualTo(Money.from(new BigDecimal(500.00)));
        assertThat(account2.getBalance()).isEqualTo(Money.from(new BigDecimal(1500.00)));
    }

    @Test
    public void shouldNotCreateTransactionWhenAccountFromIsNull() {
        Client client2 = Client.create("Jack", "Doe", "jackdoe@hotmail.com", "152.163.310-00");
        Account account2 = Account.create(client2);
        client2.addAccount(account2);
        account2.credit(Money.from(BigDecimal.valueOf(1000.00)));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Transaction.transfer(null, account2, Money.from(BigDecimal.valueOf(500.00)));
        });
        assertThat(ex.getMessage()).isEqualTo("accountFrom is required");
    }

    @Test
    public void shouldNotCreateTransactionWhenAccountToIsNull() {
        Client client1 = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        Account account1 = Account.create(client1);
        client1.addAccount(account1);
        account1.credit(Money.from(BigDecimal.valueOf(1000.00)));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Transaction.transfer(account1, null, Money.from(BigDecimal.valueOf(500.00)));
        });
        assertThat(ex.getMessage()).isEqualTo("accountTo is required");
    }

    @Test
    public void shouldNotCreateTransactionWhenAmountIsEqualToZero() {
        Client client1 = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        Client client2 = Client.create("Jack", "Doe", "jackdoe@hotmail.com", "152.163.310-00");

        Account account1 = Account.create(client1);
        Account account2 = Account.create(client2);

        client1.addAccount(account1);
        client2.addAccount(account2);

        account1.credit(Money.from(BigDecimal.valueOf(1000.00)));
        account2.credit(Money.from(BigDecimal.valueOf(1000.00)));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Transaction.transfer(account1, account2, Money.ZERO);
        });
        assertThat(ex.getMessage()).isEqualTo("amount must be greater than zero");
    }

    @Test
    public void shouldNotCreateTransactionWhenAmountIsNegative() {
        Client client1 = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        Client client2 = Client.create("Jack", "Doe", "jackdoe@hotmail.com", "152.163.310-00");

        Account account1 = Account.create(client1);
        Account account2 = Account.create(client2);

        client1.addAccount(account1);
        client2.addAccount(account2);

        account1.credit(Money.from(BigDecimal.valueOf(1000.00)));
        account2.credit(Money.from(BigDecimal.valueOf(1000.00)));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Transaction.transfer(account1, account2, Money.from(BigDecimal.valueOf(-20.00)));
        });
        assertThat(ex.getMessage()).isEqualTo("amount must be greater than zero");
    }

    @Test
    public void shouldNotCreateTransactionWhenAccountBalanceIsInsufficient() {
        Client client1 = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        Client client2 = Client.create("Jack", "Doe", "jackdoe@hotmail.com", "152.163.310-00");

        Account account1 = Account.create(client1);
        Account account2 = Account.create(client2);

        client1.addAccount(account1);
        client2.addAccount(account2);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Transaction.transfer(account1, account2, Money.from(BigDecimal.valueOf(20.00)));
        });
        assertThat(ex.getMessage()).isEqualTo("insufficient funds");
    }

    @Test
    public void shouldNotCreateTransactionWhenAccountFromIsEqualsToAccountTo() {
        Client client1 = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");

        Account account1 = Account.create(client1);

        client1.addAccount(account1);

        account1.credit(Money.from(BigDecimal.valueOf(1000.00)));

        assertThat(account1.getBalance().equals(Money.from(BigDecimal.valueOf(1000.00))));

        var ex = assertThrows(IllegalArgumentException.class, () -> {
            Transaction.transfer(account1, account1, Money.from(BigDecimal.valueOf(20.00)));
        });
        assertThat(ex.getMessage()).isEqualTo("it's not allowed to transfer funds to the same account");
    }
}
