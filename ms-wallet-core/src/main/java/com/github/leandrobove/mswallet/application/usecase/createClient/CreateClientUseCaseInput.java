package com.github.leandrobove.mswallet.application.usecase.createClient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

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

    @NotBlank
    @CPF
    private String cpf;

    public static CreateClientUseCaseInput from(final String firstName, final String lastName,
                                                final String email, final String cpf) {
        return new CreateClientUseCaseInput(firstName, lastName, email, cpf);
    }

}
