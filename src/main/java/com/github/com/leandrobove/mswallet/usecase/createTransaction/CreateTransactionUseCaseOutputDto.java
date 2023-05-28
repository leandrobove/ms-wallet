package com.github.com.leandrobove.mswallet.usecase.createTransaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CreateTransactionUseCaseOutputDto {
    @JsonProperty("id")
    private String transactionId;

    @JsonProperty("account_id_from")
    private String accountIdFrom;

    @JsonProperty("account_id_to")
    private String accountIdTo;

    private BigDecimal amount;
}
