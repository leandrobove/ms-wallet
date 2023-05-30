package com.github.leandrobove.mswallet.config;

import com.github.leandrobove.mswallet.broker.KafkaMessageBroker;
import com.github.leandrobove.mswallet.broker.MessageBrokerInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.Serializable;

@Configuration
public class MessageBrokerBean {

    @Bean
    public MessageBrokerInterface messageBrokerInterface(KafkaTemplate<String, Serializable> kafkaTemplate) {
        return new KafkaMessageBroker(kafkaTemplate);
    }
}
