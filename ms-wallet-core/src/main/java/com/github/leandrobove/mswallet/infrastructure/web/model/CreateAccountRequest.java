package com.github.leandrobove.mswallet.infrastructure.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateAccountRequest {

    @JsonProperty("client_id")
    @NotBlank
    private String clientId;
}
