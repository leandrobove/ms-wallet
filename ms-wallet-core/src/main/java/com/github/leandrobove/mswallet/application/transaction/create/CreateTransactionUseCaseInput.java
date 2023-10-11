package com.github.leandrobove.mswallet.application.transaction.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
public class CreateTransactionUseCaseInput {

    private String accountFromId;

    private String accountToId;

    private BigDecimal amount;

    public static CreateTransactionUseCaseInput from(String accountFromId, String accountToId, BigDecimal amount) {
        return new CreateTransactionUseCaseInput(accountFromId, accountToId, amount);
    }
}
