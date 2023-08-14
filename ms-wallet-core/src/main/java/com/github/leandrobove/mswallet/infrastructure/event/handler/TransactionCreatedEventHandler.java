package com.github.leandrobove.mswallet.infrastructure.event.handler;

import com.github.leandrobove.mswallet.domain.event.TransactionCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class TransactionCreatedEventHandler {
    private final StreamBridge streamBridge;

    public TransactionCreatedEventHandler(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @TransactionalEventListener
    @Async
    public void handle(TransactionCreatedEvent event) {
        streamBridge.send("transactionCreatedSupplier-out-0", event);

        log.info("TransactionCreatedEvent: {}", event);
    }
}
