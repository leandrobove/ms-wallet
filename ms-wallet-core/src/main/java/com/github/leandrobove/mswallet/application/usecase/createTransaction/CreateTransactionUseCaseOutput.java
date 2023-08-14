package com.github.leandrobove.mswallet.application.usecase.createTransaction;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Getter
@ToString
public class CreateTransactionUseCaseOutput {
    private String transactionId;
    private String accountIdFrom;
    private String accountIdTo;
    private BigDecimal amount;
}
