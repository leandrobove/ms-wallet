package com.github.com.leandrobove.mswallet.event.listener;

import com.github.com.leandrobove.mswallet.event.BalanceUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.Serializable;

@Component
public class BalanceUpdatedEventListener {
    private static final Logger log = LoggerFactory.getLogger(TransactionCreatedEventListener.class);

    private final KafkaTemplate<String, Serializable> kafkaTemplate;

    public BalanceUpdatedEventListener(KafkaTemplate<String, Serializable> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @TransactionalEventListener
    public void handle(BalanceUpdatedEvent event) {
        kafkaTemplate.send("balances", event);

        log.info("BalanceUpdatedEvent: {}", event);
    }
}
