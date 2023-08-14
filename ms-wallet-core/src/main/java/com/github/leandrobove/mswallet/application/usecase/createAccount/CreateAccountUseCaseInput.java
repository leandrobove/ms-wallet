package com.github.leandrobove.mswallet.application.usecase.createAccount;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CreateAccountUseCaseInput {

    @NotBlank
    private String clientId;

    public static CreateAccountUseCaseInput from(String clientId) {
        return new CreateAccountUseCaseInput(clientId);
    }
}
