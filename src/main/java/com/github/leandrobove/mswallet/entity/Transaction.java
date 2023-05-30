package com.github.leandrobove.mswallet.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@ToString
public class Transaction {
    @EqualsAndHashCode.Include
    private UUID id;

    private Account accountFrom;

    private Account accountTo;

    private BigDecimal amount;

    private OffsetDateTime createdAt;

    public Transaction(Account accountFrom, Account accountTo, BigDecimal amount) {
        this.id = UUID.randomUUID();
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.createdAt = OffsetDateTime.now();

        this.validate();
        this.transfer();
    }

    private void transfer() {
        this.accountFrom.debit(this.amount);
        this.accountTo.credit(this.amount);
    }

    private void validate() {
        if (this.accountFrom == null) {
            throw new IllegalArgumentException("accountFrom is required");
        }
        if (this.accountTo == null) {
            throw new IllegalArgumentException("accountTo is required");
        }
        if (this.amount == null) {
            throw new IllegalArgumentException("amount is required");
        }
        if (this.amount.doubleValue() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        if (this.accountFrom.getBalance().compareTo(this.amount) < 0) {
            throw new IllegalArgumentException("insufficient funds");
        }
    }
}
