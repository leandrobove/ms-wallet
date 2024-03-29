package com.github.leandrobove.msbalance.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class BalanceUpdatedEvent implements Serializable {

    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("account_id_from")
    private String accountIdFrom;

    @JsonProperty("account_id_to")
    private String accountIdTo;

    @JsonProperty("balance_account_from")
    private BigDecimal balanceAccountFrom;

    @JsonProperty("balance_account_to")
    private BigDecimal balanceAccountTo;

    @JsonProperty("date_time_occurred")
    private OffsetDateTime dateTimeOccurred;

}
