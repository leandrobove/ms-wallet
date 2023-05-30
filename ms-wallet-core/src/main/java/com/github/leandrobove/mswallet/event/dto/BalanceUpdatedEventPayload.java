package com.github.leandrobove.mswallet.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

public record BalanceUpdatedEventPayload(
        @JsonProperty("account_id_from") String accountIdFrom,
        @JsonProperty("account_id_to") String accountIdTo,
        @JsonProperty("balance_account_from") BigDecimal balanceAccountFrom,
        @JsonProperty("balance_account_to") BigDecimal balanceAccountTo
) implements Serializable {
}
