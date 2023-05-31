package com.github.leandrobove.mswallet.event.listener;

import com.github.leandrobove.mswallet.event.BalanceUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BalanceUpdatedEventListener {
    private static final Logger log = LoggerFactory.getLogger(TransactionCreatedEventListener.class);

    private final StreamBridge streamBridge;

    public BalanceUpdatedEventListener(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @TransactionalEventListener
    @Async
    public void handle(BalanceUpdatedEvent event) {
        streamBridge.send("balanceUpdatedSupplier-out-0", event);

        log.info("BalanceUpdatedEvent: {}", event);
    }
}
