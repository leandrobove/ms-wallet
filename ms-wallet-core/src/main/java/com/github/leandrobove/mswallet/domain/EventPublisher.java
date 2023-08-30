package com.github.leandrobove.mswallet.domain;

public interface EventPublisher {
    void publishEvent(DomainEvent event);
}
