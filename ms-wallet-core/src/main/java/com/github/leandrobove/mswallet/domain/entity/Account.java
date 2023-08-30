package com.github.leandrobove.mswallet.domain.entity;

import com.github.leandrobove.mswallet.domain.AggregateRoot;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Account extends AggregateRoot<AccountId> {

    private Client client;
    private BigDecimal balance;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    private Account(
            final AccountId id,
            final Client client,
            final BigDecimal balance,
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
        var initialAccountBalance = BigDecimal.ZERO;
        var now = OffsetDateTime.now();

        return new Account(accountId, client, initialAccountBalance, now, now);
    }

    public static Account rebuildAccount(
            final String id,
            final Client client,
            final BigDecimal balance,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        return new Account(AccountId.from(id), client, balance, createdAt, updatedAt);
    }

    private void validate() {
        if (this.client == null) {
            throw new IllegalArgumentException("client is required");
        }
    }

    public void credit(BigDecimal amount) {
        validateAmount(amount);

        this.balance = this.balance.add(amount);
        this.updatedAt = OffsetDateTime.now();
    }

    public void debit(BigDecimal amount) {
        validateAmount(amount);

        if (amount.compareTo(this.balance) > 0) {
            throw new IllegalArgumentException("insufficient funds");
        }

        this.balance = this.balance.subtract(amount);
        this.updatedAt = OffsetDateTime.now();
    }

    private static void validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("amount is required");
        }
        if (amount.doubleValue() <= 0) {
            throw new IllegalArgumentException("amount must be a positive number");
        }
    }

    public Client getClient() {
        return client;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
