package com.github.com.leandrobove.mswallet.event;

import com.github.com.leandrobove.mswallet.event.dto.TransactionCreatedEventPayload;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class TransactionCreatedEvent extends BaseEvent implements Serializable {
    private TransactionCreatedEventPayload payload;

    public TransactionCreatedEvent(TransactionCreatedEventPayload payload) {
        super("TransactionCreated");
        this.payload = payload;
    }
}
