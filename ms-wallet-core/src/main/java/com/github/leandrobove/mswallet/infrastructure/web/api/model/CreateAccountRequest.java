package com.github.leandrobove.mswallet.infrastructure.web.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {

    @JsonProperty("client_id")
    @NotBlank
    private String clientId;
}
