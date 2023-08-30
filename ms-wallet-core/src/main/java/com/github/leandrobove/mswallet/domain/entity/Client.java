package com.github.leandrobove.mswallet.domain.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Client {

    private UUID id;

    private String name;

    private String email;

    private List<Account> accounts;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Client(UUID id, String name, String email, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
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
        var clientId = UUID.randomUUID();
        var now = OffsetDateTime.now();

        return new Client(clientId, name, email, now, now);
    }

    public static Client rebuildClient(String id, String name, String email, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        return new Client(UUID.fromString(id), name, email, createdAt, updatedAt);
    }

    public void changeName(final String name) {
        this.name = name;
        this.updatedAt = OffsetDateTime.now();

        this.validate();
    }

    public void changeEmail(final String email) {
        this.email = email;
        this.updatedAt = OffsetDateTime.now();

        this.validate();
    }

    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("account is required");
        }
        if (account.getClient().getId() != this.id) {
            throw new IllegalArgumentException("account does not belong to client");
        }
        this.accounts.add(account);
    }

    private void validate() {
        if (this.name == "") {
            throw new IllegalArgumentException("name is required");
        }
        if (this.email == "") {
            throw new IllegalArgumentException("email is required");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id.equals(client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", accounts=" + accounts +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
