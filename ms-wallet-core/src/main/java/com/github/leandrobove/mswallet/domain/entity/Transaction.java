package com.github.leandrobove.mswallet.domain.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class Transaction {
    private UUID id;

    private Account accountFrom;

    private Account accountTo;

    private BigDecimal amount;

    private OffsetDateTime createdAt;

    private Transaction(
            final Account accountFrom,
            final Account accountTo,
            final BigDecimal amount
    ) {
        var transactionId = UUID.randomUUID();
        var now = OffsetDateTime.now();

        this.id = transactionId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.createdAt = now;

        this.validate();
    }

    public static Transaction transfer(
            final Account accountFrom,
            final Account accountTo,
            final BigDecimal amount
    ) {
        var transaction = new Transaction(accountFrom, accountTo, amount);
        transaction.getAccountFrom().debit(amount);
        transaction.getAccountTo().credit(amount);

        return transaction;
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

    public UUID getId() {
        return id;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                '}';
    }
}
