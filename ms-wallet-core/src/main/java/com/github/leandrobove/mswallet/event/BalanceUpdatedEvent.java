package com.github.leandrobove.mswallet.event;

import com.github.leandrobove.mswallet.event.dto.BalanceUpdatedEventPayload;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class BalanceUpdatedEvent extends BaseEvent implements Serializable {
    private BalanceUpdatedEventPayload payload;

    public BalanceUpdatedEvent(BalanceUpdatedEventPayload payload) {
        super("BalanceUpdated");
        this.payload = payload;
    }
}

