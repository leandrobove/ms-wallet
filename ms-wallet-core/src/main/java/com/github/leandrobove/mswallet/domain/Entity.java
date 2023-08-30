package com.github.leandrobove.mswallet.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Entity<ID extends DomainObjectId> implements DomainObject {

    @EqualsAndHashCode.Include
    protected ID id;

    protected Entity(final ID id) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
    }
}
