package com.github.leandrobove.mswallet.infrastructure.event.publisher;

import com.github.leandrobove.mswallet.domain.DomainEvent;
import com.github.leandrobove.mswallet.domain.EventPublisher;
import com.github.leandrobove.mswallet.domain.event.BalanceUpdatedEvent;
import com.github.leandrobove.mswallet.domain.event.TransactionCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventPublisherStream implements EventPublisher {

    private final StreamBridge streamBridge;

    public EventPublisherStream(final StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public void publishEvent(DomainEvent event) {
        String bindingName = "";

        // TODO: 30/08/2023 refactor this class
        if (event instanceof BalanceUpdatedEvent) {
            bindingName = "balanceUpdatedSupplier-out-0";
        } else if (event instanceof TransactionCreatedEvent) {
            bindingName = "transactionCreatedSupplier-out-0";
        }

        streamBridge.send(bindingName, event);

        log.info(event.getClass().getSimpleName() + ": {}", event);
    }
}
