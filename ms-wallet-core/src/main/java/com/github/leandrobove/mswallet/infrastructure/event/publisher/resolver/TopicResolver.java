package com.github.leandrobove.mswallet.infrastructure.event.publisher.resolver;

import com.github.leandrobove.mswallet.domain.DomainEvent;

public interface TopicResolver {

    String resolveTopic(DomainEvent event);
}
