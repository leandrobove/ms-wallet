package com.github.leandrobove.mswallet.domain.entity;

import com.github.leandrobove.mswallet.domain.AggregateRoot;
import com.github.leandrobove.mswallet.domain.event.AccountCreatedEvent;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Account extends AggregateRoot<AccountId> {

    private Client client;
    private Money balance;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    private Account(
            final AccountId id,
            final Client client,
            final Money balance,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        super(id);
        this.client = client;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.validate();
    }

    public static Account create(final Client client) {
        var accountId = AccountId.unique();
        var initialAccountBalance = Money.ZERO;
        var now = OffsetDateTime.now();

        Account account = new Account(accountId, client, initialAccountBalance, now, now);

        account.registerEvent(new AccountCreatedEvent(account));

        return account;
    }

    public static Account rebuildAccount(
            final String id,
            final Client client,
            final BigDecimal balance,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        return new Account(AccountId.from(id), client, Money.from(balance, null), createdAt, updatedAt);
    }

    private void validate() {
        if (this.client == null) {
            throw new IllegalArgumentException("client is required");
        }
    }

    public void credit(final Money amount) {
        validateAmount(amount);

        this.balance = this.balance.add(amount);
        this.updatedAt = OffsetDateTime.now();
    }

    public void debit(final Money amount) {
        validateAmount(amount);

        if (amount.isGreaterThan(this.balance)) {
            throw new IllegalArgumentException("insufficient funds");
        }

        this.balance = this.balance.subtract(amount);
        this.updatedAt = OffsetDateTime.now();
    }

    private static void validateAmount(final Money amount) {
        if (amount == null) {
            throw new IllegalArgumentException("amount is required");
        }
        if (amount.isLessThanOrEqualTo(Money.ZERO)) {
            throw new IllegalArgumentException("amount must be a positive number");
        }
    }

    public Client getClient() {
        return client;
    }

    public Money getBalance() {
        return balance;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
