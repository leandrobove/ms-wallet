package com.github.leandrobove.mswallet.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot<ID extends DomainObjectId> extends Entity<ID> {

    private final List<DomainEvent> domainEvents;

    protected AggregateRoot(final ID id, List<DomainEvent> domainEvents) {
        super(id);
        this.domainEvents = new ArrayList<>(domainEvents == null ? Collections.emptyList() : domainEvents);
    }

    protected AggregateRoot(final ID id) {
        this(id, null);
    }

    public void publishDomainEvents(final EventPublisher publisher) {
        if (publisher == null) {
            return;
        }

        getDomainEvents().forEach(publisher::publishEvent);

        this.domainEvents.clear();
    }

    public void registerEvent(final DomainEvent event) {
        if (event == null) {
            return;
        }

        this.domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
}
