package com.github.leandrobove.msbalance.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BalanceUpdatedEventPayload implements Serializable {
    @JsonProperty("account_id_from")
    private String accountIdFrom;
    @JsonProperty("account_id_to")
    private String accountIdTo;
    @JsonProperty("balance_account_from")
    private BigDecimal balanceAccountFrom;
    @JsonProperty("balance_account_to")
    private BigDecimal balanceAccountTo;
}