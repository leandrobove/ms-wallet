package com.github.leandrobove.mswallet.broker;

import com.github.leandrobove.mswallet.event.BaseEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class KafkaMessageBroker implements MessageBrokerInterface<BaseEvent> {

    private final KafkaTemplate<String, Serializable> kafkaTemplate;

    public KafkaMessageBroker(KafkaTemplate<String, Serializable> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishMessage(String topic, BaseEvent message) {
        kafkaTemplate.send(topic, message);
    }
}
