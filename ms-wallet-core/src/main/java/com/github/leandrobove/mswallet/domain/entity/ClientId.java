package com.github.leandrobove.mswallet.domain.entity;

import com.github.leandrobove.mswallet.domain.DomainObjectId;
import com.github.leandrobove.mswallet.domain.IdUtils;

import java.util.Objects;

public final class ClientId extends DomainObjectId {

    private final String id;

    private ClientId(final String id) {
        this.id = Objects.requireNonNull(id);
    }

    public static ClientId unique() {
        return ClientId.from(IdUtils.uuid());
    }

    public static ClientId from(final String anId) {
        return new ClientId(anId);
    }

    @Override
    public String value() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientId clientId = (ClientId) o;
        return id.equals(clientId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ClientId{" +
                "id='" + id + '\'' +
                '}';
    }
}
