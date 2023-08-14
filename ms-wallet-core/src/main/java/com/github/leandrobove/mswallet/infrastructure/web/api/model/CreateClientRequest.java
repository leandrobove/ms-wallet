package com.github.leandrobove.mswallet.infrastructure.web.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateClientRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;
}
