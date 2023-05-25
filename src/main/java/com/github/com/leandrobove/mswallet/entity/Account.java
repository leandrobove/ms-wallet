package com.github.com.leandrobove.mswallet.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@ToString
public class Account {
    @EqualsAndHashCode.Include
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

    public static Account create(Client client) {
        return new Account(UUID.randomUUID(), client, BigDecimal.ZERO, OffsetDateTime.now(), OffsetDateTime.now());
    }

    public static Account createWithId(String id, Client client) {
        return new Account(UUID.fromString(id), client, BigDecimal.ZERO, OffsetDateTime.now(), OffsetDateTime.now());
    }

    public void validate() {
        if (this.client == null) {
            throw new IllegalArgumentException("client is required");
        }
    }

    public void credit(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("amount is required");
        }
        if (amount.doubleValue() <= 0) {
            throw new IllegalArgumentException("amount must be a positive number");
        }

        this.balance = this.balance.add(amount);
        this.updatedAt = OffsetDateTime.now();
    }

    public void debit(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("amount is required");
        }
        if (amount.doubleValue() <= 0) {
            throw new IllegalArgumentException("amount must be a positive number");
        }
        if (amount.compareTo(this.balance) > 0) {
            throw new IllegalArgumentException("insufficient funds");
        }

        this.balance = this.balance.subtract(amount);
        this.updatedAt = OffsetDateTime.now();
    }
}
