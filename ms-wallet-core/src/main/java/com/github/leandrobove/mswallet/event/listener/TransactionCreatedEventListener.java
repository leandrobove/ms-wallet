package com.github.leandrobove.mswallet.event.listener;

import com.github.leandrobove.mswallet.event.TransactionCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TransactionCreatedEventListener {
    private static final Logger log = LoggerFactory.getLogger(TransactionCreatedEventListener.class);

    private final StreamBridge streamBridge;

    public TransactionCreatedEventListener(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @TransactionalEventListener
    @Async
    public void handle(TransactionCreatedEvent event) {
        streamBridge.send("transactionCreatedSupplier-out-0", event);

        log.info("TransactionCreatedEvent: {}", event);
    }
}
