package com.github.leandrobove.mswallet.application.usecase.createAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CreateAccountUseCaseInput {

    private String clientId;

    public static CreateAccountUseCaseInput from(String clientId) {
        return new CreateAccountUseCaseInput(clientId);
    }
}
