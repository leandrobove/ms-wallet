package com.github.leandrobove.mswallet.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.leandrobove.mswallet.domain.DomainEvent;
import com.github.leandrobove.mswallet.domain.entity.Account;
import com.github.leandrobove.mswallet.domain.entity.Transaction;

import java.math.BigDecimal;

public class TransactionCreatedEvent extends DomainEvent {

    @JsonProperty("id")
    private String transactionId;

    @JsonProperty("account_id_from")
    private String accountIdFrom;

    @JsonProperty("account_id_to")
    private String accountIdTo;

    @JsonProperty("amount")
    private BigDecimal amount;

    public TransactionCreatedEvent(Transaction transaction) {
        super();

        Account accountFrom = transaction.getAccountFrom();
        Account accountTo = transaction.getAccountTo();

        this.transactionId = transaction.getId().value();
        this.accountIdFrom = accountFrom.getId().value();
        this.accountIdTo = accountTo.getId().value();
        this.amount = transaction.getAmount();
    }

    @Override
    public String toString() {
        return "TransactionCreatedEvent{" +
                "transactionId='" + transactionId + '\'' +
                ", accountIdFrom='" + accountIdFrom + '\'' +
                ", accountIdTo='" + accountIdTo + '\'' +
                ", amount=" + amount +
                ", eventId='" + eventId + '\'' +
                ", dateTimeOccurred=" + dateTimeOccurred +
                '}';
    }
}
