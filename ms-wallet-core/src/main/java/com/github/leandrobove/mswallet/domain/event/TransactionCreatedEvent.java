package com.github.leandrobove.mswallet.domain.event;

import com.github.leandrobove.mswallet.domain.BaseEvent;
import com.github.leandrobove.mswallet.infrastructure.event.dto.TransactionCreatedEventPayload;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TransactionCreatedEvent extends BaseEvent {
    private TransactionCreatedEventPayload payload;

    public TransactionCreatedEvent(TransactionCreatedEventPayload payload) {
        super("TransactionCreated");
        this.payload = payload;
    }
}
