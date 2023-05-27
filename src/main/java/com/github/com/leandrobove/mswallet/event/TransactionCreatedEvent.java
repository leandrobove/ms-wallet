package com.github.com.leandrobove.mswallet.event;

import org.springframework.context.ApplicationEvent;

public class TransactionCreatedEvent extends ApplicationEvent {
    private Object payload;

    public TransactionCreatedEvent(Object source, Object payload) {
        super(source);
        this.payload = payload;
    }
}
