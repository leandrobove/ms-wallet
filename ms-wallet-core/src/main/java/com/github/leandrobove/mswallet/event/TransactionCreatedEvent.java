package com.github.leandrobove.mswallet.event;

import com.github.leandrobove.mswallet.event.dto.TransactionCreatedEventPayload;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class TransactionCreatedEvent extends BaseEvent implements Serializable {
    private TransactionCreatedEventPayload payload;

    public TransactionCreatedEvent(TransactionCreatedEventPayload payload) {
        super("TransactionCreated");
        this.payload = payload;
    }
}
