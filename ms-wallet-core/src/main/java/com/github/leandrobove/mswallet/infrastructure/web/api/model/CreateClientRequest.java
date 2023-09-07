package com.github.leandrobove.mswallet.infrastructure.web.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
public class CreateClientRequest {

    @JsonProperty("first_name")
    @NotBlank
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank
    private String lastName;

    @Email
    private String email;

    @CPF
    private String cpf;
}
