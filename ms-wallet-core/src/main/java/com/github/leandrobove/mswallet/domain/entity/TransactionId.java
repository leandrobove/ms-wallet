package com.github.leandrobove.mswallet.domain.entity;

import com.github.leandrobove.mswallet.domain.DomainObjectId;
import com.github.leandrobove.mswallet.domain.IdUtils;

import java.util.Objects;

public final class TransactionId extends DomainObjectId {

    private final String id;

    private TransactionId(final String id) {
        this.id = Objects.requireNonNull(id);
    }

    public static TransactionId unique() {
        return TransactionId.from(IdUtils.uuid());
    }

    public static TransactionId from(final String anId) {
        return new TransactionId(anId);
    }

    @Override
    public String value() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionId transactionId = (TransactionId) o;
        return id.equals(transactionId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TransactionId{" +
                "id='" + id + '\'' +
                '}';
    }
}
