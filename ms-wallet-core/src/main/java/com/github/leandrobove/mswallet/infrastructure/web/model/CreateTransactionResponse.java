package com.github.leandrobove.mswallet.infrastructure.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CreateTransactionResponse(
        @JsonProperty("id") String transactionId,
        @JsonProperty("account_id_from") String accountIdFrom,
        @JsonProperty("account_id_to") String accountIdTo,
        BigDecimal amount
) {
}
