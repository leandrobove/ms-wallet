package com.github.leandrobove.mswallet.usecase.createTransaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Getter
@ToString
public class CreateTransactionUseCaseOutputDto implements Serializable {
    @JsonProperty("id")
    private String transactionId;

    @JsonProperty("account_id_from")
    private String accountIdFrom;

    @JsonProperty("account_id_to")
    private String accountIdTo;

    private BigDecimal amount;
}
