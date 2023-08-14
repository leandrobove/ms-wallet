package com.github.leandrobove.mswallet.domain.event;

import com.github.leandrobove.mswallet.domain.BaseEvent;
import com.github.leandrobove.mswallet.infrastructure.event.dto.BalanceUpdatedEventPayload;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BalanceUpdatedEvent extends BaseEvent {
    private BalanceUpdatedEventPayload payload;

    public BalanceUpdatedEvent(BalanceUpdatedEventPayload payload) {
        super("BalanceUpdated");
        this.payload = payload;
    }
}

