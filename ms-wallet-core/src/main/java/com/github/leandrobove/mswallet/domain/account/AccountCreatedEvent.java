package com.github.leandrobove.mswallet.domain.account;

import com.github.leandrobove.mswallet.domain.DomainEvent;

import java.math.BigDecimal;

public class AccountCreatedEvent extends DomainEvent {

    private String accountId;
    private String clientId;
    private BigDecimal balance;

    public AccountCreatedEvent(final Account account) {
        this.accountId = account.getId().value();
        this.clientId = account.getClient().getId().value();
        this.balance = account.getBalance().value();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getClientId() {
        return clientId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "AccountCreatedEvent{" +
                "accountId='" + accountId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", balance=" + balance +
                ", eventId='" + eventId + '\'' +
                ", dateTimeOccurred=" + dateTimeOccurred +
                '}';
    }
}
