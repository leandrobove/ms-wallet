package com.github.leandrobove.mswallet.domain;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public abstract class DomainEvent implements DomainObject {

    protected final String eventId;
    protected final OffsetDateTime dateTimeOccurred;

    protected DomainEvent() {
        this.eventId = IdUtils.uuid();
        this.dateTimeOccurred = OffsetDateTime.now();
    }

    public OffsetDateTime getDateTimeOccurred() {
        return dateTimeOccurred;
    }

    public String getEventId() {
        return eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainEvent that = (DomainEvent) o;
        return eventId.equals(that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    @Override
    public String toString() {
        return "DomainEvent{" +
                "eventId='" + eventId + '\'' +
                ", dateTimeOccurred=" + dateTimeOccurred +
                '}';
    }
}
