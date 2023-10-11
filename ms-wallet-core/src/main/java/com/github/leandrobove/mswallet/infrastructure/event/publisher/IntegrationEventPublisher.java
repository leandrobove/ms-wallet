package com.github.leandrobove.mswallet.infrastructure.event.publisher;

import com.github.leandrobove.mswallet.domain.DomainEvent;
import com.github.leandrobove.mswallet.domain.EventPublisher;
import com.github.leandrobove.mswallet.infrastructure.event.publisher.resolver.TopicResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class IntegrationEventPublisher implements EventPublisher {

    private final StreamBridge streamBridge;
    private final TopicResolver topicResolver;

    public IntegrationEventPublisher(final StreamBridge streamBridge, final TopicResolver topicResolver) {
        this.streamBridge = streamBridge;
        this.topicResolver = topicResolver;
    }

    @Override
    public void publishEvent(DomainEvent event) {
        String binderName = topicResolver.resolveTopic(event);

        streamBridge.send(binderName, event);

        log.info(event.getClass().getSimpleName() + ": {}", event);
    }
}
