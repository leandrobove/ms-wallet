package com.github.leandrobove.mswallet.application.usecase.createClient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateClientUseCaseInput {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    public static CreateClientUseCaseInput from(String name, String email) {
        return new CreateClientUseCaseInput(name, email);
    }

}
