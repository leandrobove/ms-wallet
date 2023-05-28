package com.github.com.leandrobove.mswallet.usecase.createTransaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CreateTransactionUseCaseInputDto {

    @JsonProperty("account_id_from")
    private String accountFromId;

    @JsonProperty("account_id_to")
    private String accountToId;

    private BigDecimal amount;
}
