package com.github.leandrobove.mswallet.event.listener;

import com.github.leandrobove.mswallet.broker.MessageBrokerInterface;
import com.github.leandrobove.mswallet.event.TransactionCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TransactionCreatedEventListener {
    private static final Logger log = LoggerFactory.getLogger(TransactionCreatedEventListener.class);

    private final MessageBrokerInterface<TransactionCreatedEvent> broker;

    public TransactionCreatedEventListener(MessageBrokerInterface<TransactionCreatedEvent> broker) {
        this.broker = broker;
    }

    @TransactionalEventListener
    public void handle(TransactionCreatedEvent event) {
        broker.publishMessage("transactions", event);

        log.info("TransactionCreatedEvent: {}", event);
    }
}
