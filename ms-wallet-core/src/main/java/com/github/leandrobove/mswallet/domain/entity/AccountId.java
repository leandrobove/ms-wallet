package com.github.leandrobove.mswallet.domain.entity;

import com.github.leandrobove.mswallet.domain.DomainObjectId;
import com.github.leandrobove.mswallet.domain.IdUtils;

import java.util.Objects;

public final class AccountId extends DomainObjectId {

    private final String id;

    private AccountId(final String id) {
        this.id = Objects.requireNonNull(id);
    }

    public static AccountId unique() {
        return AccountId.from(IdUtils.uuid());
    }

    public static AccountId from(final String anId) {
        return new AccountId(anId);
    }

    @Override
    public String value() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountId accountId = (AccountId) o;
        return id.equals(accountId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AccountId{" +
                "id='" + id + '\'' +
                '}';
    }
}
