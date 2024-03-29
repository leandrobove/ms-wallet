package com.github.leandrobove.mswallet.domain.client;

import com.github.leandrobove.mswallet.domain.AggregateRoot;
import com.github.leandrobove.mswallet.domain.account.Account;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Client extends AggregateRoot<ClientId> {

    private FullName fullName;
    private Email email;
    private CPF cpf;
    private List<Account> accounts;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    private Client(
            final ClientId id,
            final FullName fullName,
            final Email email,
            final CPF cpf,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        super(id);
        this.fullName = fullName;
        this.email = email;
        this.cpf = cpf;
        this.accounts = new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Client create(
            final String firstName,
            final String lastName,
            final String email,
            final String cpf
    ) {
        var clientId = ClientId.unique();
        var now = OffsetDateTime.now();

        return new Client(clientId, FullName.from(firstName, lastName), Email.from(email), CPF.from(cpf), now, now);
    }

    public static Client rebuildClient(
            final String id,
            final String fullName,
            final String email,
            final String cpf,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        return new Client(ClientId.from(id), FullName.from(fullName), Email.from(email), CPF.from(cpf), createdAt, updatedAt);
    }

    public void changeName(final FullName fullName) {
        this.fullName = fullName;
        this.updatedAt = OffsetDateTime.now();
    }

    public void changeEmail(final Email email) {
        this.email = email;
        this.updatedAt = OffsetDateTime.now();
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

    public FullName getName() {
        return fullName;
    }

    public Email getEmail() {
        return email;
    }

    public CPF getCpf() {
        return cpf;
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
