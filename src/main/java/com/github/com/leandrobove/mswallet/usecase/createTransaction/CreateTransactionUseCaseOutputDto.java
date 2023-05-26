package com.github.com.leandrobove.mswallet.usecase.createTransaction;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateTransactionUseCaseOutputDto {

    private String transactionId;
}
