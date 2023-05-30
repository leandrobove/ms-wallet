package com.github.com.leandrobove.mswallet.event.listener;

import com.github.com.leandrobove.mswallet.broker.MessageBrokerInterface;
import com.github.com.leandrobove.mswallet.event.BalanceUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class BalanceUpdatedEventListener {
    private static final Logger log = LoggerFactory.getLogger(TransactionCreatedEventListener.class);

    private final MessageBrokerInterface<BalanceUpdatedEvent> broker;

    public BalanceUpdatedEventListener(MessageBrokerInterface<BalanceUpdatedEvent> broker) {
        this.broker = broker;
    }

    @TransactionalEventListener
    public void handle(BalanceUpdatedEvent event) {
        broker.publishMessage("balances", event);

        log.info("BalanceUpdatedEvent: {}", event);
    }
}
