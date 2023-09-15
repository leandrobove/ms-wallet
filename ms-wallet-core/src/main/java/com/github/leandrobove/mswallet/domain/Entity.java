package com.github.leandrobove.mswallet.domain;

import java.util.Objects;

public abstract class Entity<ID extends DomainObjectId> implements DomainObject {

    protected final ID id;

    protected Entity(final ID id) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
    }

    public ID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
