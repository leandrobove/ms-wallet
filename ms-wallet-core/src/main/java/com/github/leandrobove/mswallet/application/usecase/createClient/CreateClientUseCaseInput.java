package com.github.leandrobove.mswallet.application.usecase.createClient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateClientUseCaseInput {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    public static CreateClientUseCaseInput from(String firstName, String lastName, String email) {
        return new CreateClientUseCaseInput(firstName, lastName, email);
    }

}
