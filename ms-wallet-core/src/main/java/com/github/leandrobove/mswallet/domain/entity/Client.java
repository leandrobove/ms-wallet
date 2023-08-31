package com.github.leandrobove.mswallet.domain.entity;

import com.github.leandrobove.mswallet.domain.AggregateRoot;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Client extends AggregateRoot<ClientId> {

    private String name;
    private Email email;
    private List<Account> accounts;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    private Client(
            final ClientId id,
            final String name,
            final Email email,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        super(id);
        this.name = name;
        this.email = email;
        this.accounts = new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.validate();
    }

    public static Client create(
            final String name,
            final String email
    ) {
        var clientId = ClientId.unique();
        var now = OffsetDateTime.now();

        return new Client(clientId, name, Email.from(email), now, now);
    }

    public static Client rebuildClient(
            final String id,
            final String name,
            final String email,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        return new Client(ClientId.from(id), name, Email.from(email), createdAt, updatedAt);
    }

    public void changeName(final String name) {
        this.name = name;
        this.updatedAt = OffsetDateTime.now();

        this.validate();
    }

    public void changeEmail(final String email) {
        this.email = Email.from(email);
        this.updatedAt = OffsetDateTime.now();

        this.validate();
    }

    public void addAccount(final Account account) {
        if (account == null) {
            throw new IllegalArgumentException("account is required");
        }
        if (account.getClient().getId() != this.id) {
            throw new IllegalArgumentException("account does not belong to client");
        }
        this.accounts.add(account);
    }

    private void validate() {
        if (this.name == "" || this.name == null) {
            throw new IllegalArgumentException("name is required");
        }
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

}
