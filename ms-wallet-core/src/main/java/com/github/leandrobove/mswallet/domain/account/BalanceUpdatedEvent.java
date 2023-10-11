package com.github.leandrobove.mswallet.domain.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.leandrobove.mswallet.domain.DomainEvent;
import com.github.leandrobove.mswallet.domain.transaction.Transaction;

import java.math.BigDecimal;

public class BalanceUpdatedEvent extends DomainEvent {

    @JsonProperty("account_id_from")
    private String accountIdFrom;

    @JsonProperty("account_id_to")
    private String accountIdTo;

    @JsonProperty("balance_account_from")
    private BigDecimal balanceAccountFrom;

    @JsonProperty("balance_account_to")
    private BigDecimal balanceAccountTo;

    public BalanceUpdatedEvent(Transaction transaction) {
        super();

        Account accountFrom = transaction.getAccountFrom();
        Account accountTo = transaction.getAccountTo();

        this.accountIdFrom = accountFrom.getId().value();
        this.balanceAccountFrom = accountFrom.getBalance().value();
        this.accountIdTo = accountTo.getId().value();
        this.balanceAccountTo = accountTo.getBalance().value();
    }

    @Override
    public String toString() {
        return "BalanceUpdatedEvent{" +
                "accountIdFrom='" + accountIdFrom + '\'' +
                ", accountIdTo='" + accountIdTo + '\'' +
                ", balanceAccountFrom=" + balanceAccountFrom +
                ", balanceAccountTo=" + balanceAccountTo +
                ", eventId='" + eventId + '\'' +
                ", dateTimeOccurred=" + dateTimeOccurred +
                '}';
    }
}

