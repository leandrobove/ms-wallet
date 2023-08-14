package com.github.leandrobove.mswallet.infrastructure.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

public record TransactionCreatedEventPayload(
        @JsonProperty("id") String transactionId,
        @JsonProperty("account_id_from") String accountIdFrom,
        @JsonProperty("account_id_to") String accountIdTo,
        BigDecimal amount
) implements Serializable {
}
