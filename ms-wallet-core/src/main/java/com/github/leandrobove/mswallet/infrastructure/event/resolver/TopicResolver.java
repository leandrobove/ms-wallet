package com.github.leandrobove.mswallet.infrastructure.event.resolver;

import com.github.leandrobove.mswallet.domain.DomainEvent;

public interface TopicResolver {

    String resolveTopic(DomainEvent event);
}
