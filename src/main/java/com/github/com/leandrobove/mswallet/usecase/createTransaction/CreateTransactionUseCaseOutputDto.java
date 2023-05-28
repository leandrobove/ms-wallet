package com.github.com.leandrobove.mswallet.usecase.createTransaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateTransactionUseCaseOutputDto {
    @JsonProperty("id")
    private String transactionId;
}
