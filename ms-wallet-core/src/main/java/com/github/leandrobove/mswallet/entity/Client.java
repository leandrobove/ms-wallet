package com.github.leandrobove.mswallet.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@ToString
public class Client {

    @EqualsAndHashCode.Include
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

    public static Client create(String name, String email) {
        return new Client(UUID.randomUUID(), name, email, OffsetDateTime.now(), OffsetDateTime.now());
    }

    public static Client createWithId(String id, String name, String email) {
        return new Client(UUID.fromString(id), name, email, OffsetDateTime.now(), OffsetDateTime.now());
    }

    public static Client rebuildClient(String id, String name, String email, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        return new Client(UUID.fromString(id), name, email, createdAt, updatedAt);
    }

    public void changeName(String name) {
        this.name = name;
        this.updatedAt = OffsetDateTime.now();

        this.validate();
    }

    public void changeEmail(String email) {
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
}
