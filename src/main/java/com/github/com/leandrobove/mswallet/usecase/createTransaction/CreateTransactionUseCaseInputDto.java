package com.github.com.leandrobove.mswallet.usecase.createTransaction;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CreateTransactionUseCaseInputDto {

    private String accountFromId;
    private String accountToId;
    private BigDecimal amount;
}
