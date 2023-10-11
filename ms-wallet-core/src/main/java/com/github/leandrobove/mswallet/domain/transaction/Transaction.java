package com.github.leandrobove.mswallet.domain.transaction;

import com.github.leandrobove.mswallet.domain.AggregateRoot;
import com.github.leandrobove.mswallet.domain.Money;
import com.github.leandrobove.mswallet.domain.account.Account;
import com.github.leandrobove.mswallet.domain.account.BalanceUpdatedEvent;

import java.time.OffsetDateTime;

public class Transaction extends AggregateRoot<TransactionId> {

    private Account accountFrom;
    private Account accountTo;
    private Money amount;
    private OffsetDateTime createdAt;

    private Transaction(
            final TransactionId id,
            final Account accountFrom,
            final Account accountTo,
            final Money amount,
            final OffsetDateTime createdAt
    ) {
        super(id);

        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
        this.createdAt = createdAt;

        this.validate();
    }

    public static Transaction transfer(
            final Account accountFrom,
            final Account accountTo,
            final Money amount
    ) {
        var transactionId = TransactionId.unique();
        var now = OffsetDateTime.now();

        var transaction = new Transaction(transactionId, accountFrom, accountTo, amount, now);
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
        if (this.amount.isLessThanOrEqualTo(Money.ZERO)) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        if (this.accountFrom.getBalance().isLessThan(this.amount)) {
            throw new IllegalArgumentException("insufficient funds");
        }
        if (this.accountFrom.equals(accountTo)) {
            throw new IllegalArgumentException("it's not allowed to transfer funds to the same account");
        }
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public Money getAmount() {
        return amount;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
