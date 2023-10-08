package com.github.leandrobove.mswallet.application.usecase.createClient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CreateClientUseCaseInput {

    private String firstName;
    private String lastName;
    private String email;
    private String cpf;

    public static CreateClientUseCaseInput from(
            final String firstName,
            final String lastName,
            final String email,
            final String cpf
    ) {
        return new CreateClientUseCaseInput(firstName, lastName, email, cpf);
    }

}
