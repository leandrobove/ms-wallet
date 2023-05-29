package com.github.com.leandrobove.mswallet.event.listener;

import com.github.com.leandrobove.mswallet.event.TransactionCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.Serializable;

@Component
public class TransactionCreatedEventListener {
    private static final Logger log = LoggerFactory.getLogger(TransactionCreatedEventListener.class);

    private final KafkaTemplate<String, Serializable> kafkaTemplate;

    public TransactionCreatedEventListener(KafkaTemplate<String, Serializable> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @TransactionalEventListener
    public void handle(TransactionCreatedEvent event) {
        // TODO: 29/05/2023  send message to kafka topic
        kafkaTemplate.send("transactions", event);

        log.info("TransactionCreatedEvent: {}", event);
    }
}
