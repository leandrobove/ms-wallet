package com.github.leandrobove.mswallet.domain.entity;

import com.github.leandrobove.mswallet.domain.AggregateRoot;
import com.github.leandrobove.mswallet.domain.event.BalanceUpdatedEvent;
import com.github.leandrobove.mswallet.domain.event.TransactionCreatedEvent;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Transaction extends AggregateRoot<TransactionId> {

    private Account accountFrom;
    private Account accountTo;
    private BigDecimal amount;
    private OffsetDateTime createdAt;

    private Transaction(
            final Account accountFrom,
            final Account accountTo,
            final BigDecimal amount
    ) {
        super(TransactionId.unique());

        var now = OffsetDateTime.now();

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

        transaction.registerEvent(new BalanceUpdatedEvent(transaction));
        transaction.registerEvent(new TransactionCreatedEvent(transaction));

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
}
