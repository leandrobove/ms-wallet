package com.github.leandrobove.mswallet.infrastructure.web.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest {

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
