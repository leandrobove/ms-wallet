package com.github.leandrobove.mswallet.application.usecase.createTransaction;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CreateTransactionUseCaseInput {

    @NotBlank
    private String accountFromId;

    @NotBlank
    private String accountToId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    public static CreateTransactionUseCaseInput from(String accountFromId, String accountToId, BigDecimal amount) {
        return new CreateTransactionUseCaseInput(accountFromId, accountToId, amount);
    }
}
