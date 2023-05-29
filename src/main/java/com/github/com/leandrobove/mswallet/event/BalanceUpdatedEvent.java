package com.github.com.leandrobove.mswallet.event;

import com.github.com.leandrobove.mswallet.event.dto.BalanceUpdatedEventPayload;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class BalanceUpdatedEvent extends BaseEvent implements Serializable {
    private BalanceUpdatedEventPayload payload;

    public BalanceUpdatedEvent(BalanceUpdatedEventPayload payload) {
        super("BalanceUpdated");
        this.payload = payload;
    }
}

