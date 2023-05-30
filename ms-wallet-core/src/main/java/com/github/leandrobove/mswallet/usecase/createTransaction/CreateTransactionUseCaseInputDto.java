package com.github.leandrobove.mswallet.usecase.createTransaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class CreateTransactionUseCaseInputDto {

    @JsonProperty("account_id_from")
    @NotBlank
    private String accountFromId;

    @JsonProperty("account_id_to")
    @NotBlank
    private String accountToId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;
}
