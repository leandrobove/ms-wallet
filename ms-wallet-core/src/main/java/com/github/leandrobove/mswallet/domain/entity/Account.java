package com.github.leandrobove.mswallet.domain.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class Account {
    private UUID id;

    private Client client;

    private BigDecimal balance;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Account(UUID id, Client client, BigDecimal balance, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.client = client;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.validate();
    }

    public static Account create(final Client client) {
        var accountId = UUID.randomUUID();
        var initialAccountBalance = BigDecimal.ZERO;
        var now = OffsetDateTime.now();

        return new Account(accountId, client, initialAccountBalance, now, now);
    }

    public static Account rebuildAccount(String id, Client client, BigDecimal balance, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        return new Account(UUID.fromString(id), client, balance, createdAt, updatedAt);
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

    public UUID getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", client=" + client +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
