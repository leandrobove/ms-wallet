package com.github.leandrobove.msbalance.event;

import com.github.leandrobove.msbalance.event.dto.BalanceUpdatedEventPayload;
import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class BalanceUpdatedEvent implements Serializable {
    private BalanceUpdatedEventPayload payload;
    private OffsetDateTime timestamp;
    private String name;
}
