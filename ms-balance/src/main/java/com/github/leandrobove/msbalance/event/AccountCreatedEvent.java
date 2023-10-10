package com.github.leandrobove.msbalance.event;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class AccountCreatedEvent implements Serializable {

    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("balance")
    private BigDecimal balance;

    @JsonProperty("date_time_occurred")
    private OffsetDateTime dateTimeOccurred;
}
