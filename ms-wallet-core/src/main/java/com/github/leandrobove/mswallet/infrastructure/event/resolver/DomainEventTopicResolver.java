package com.github.leandrobove.mswallet.infrastructure.event.resolver;

import com.github.leandrobove.mswallet.domain.DomainEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DomainEventTopicResolver implements TopicResolver {

    private static final Map<String, String> eventTopicMap = new HashMap<>();

    static {
        eventTopicMap.put("BalanceUpdatedEvent", "balanceUpdatedSupplier-out-0");
        eventTopicMap.put("TransactionCreatedEvent", "transactionCreatedSupplier-out-0");
    }

    @Override
    public String resolveTopic(final DomainEvent event) {
        String eventName = event.getClass().getSimpleName();

        if (eventTopicMap.get(eventName).isEmpty()) {
            throw new IllegalArgumentException("Event not resolved");
        }

        return eventTopicMap.get(eventName);
    }
}
