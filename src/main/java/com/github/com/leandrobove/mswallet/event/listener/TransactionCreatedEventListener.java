package com.github.com.leandrobove.mswallet.event.listener;

import com.github.com.leandrobove.mswallet.event.TransactionCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TransactionCreatedEventListener {
    private static final Logger log = LoggerFactory.getLogger(TransactionCreatedEventListener.class);

    @TransactionalEventListener
    public void handle(TransactionCreatedEvent event) {
        // TODO: 29/05/2023  send message to kafka topic
        log.info("TransactionCreatedEvent: {}", event.getPayload());
    }
}
