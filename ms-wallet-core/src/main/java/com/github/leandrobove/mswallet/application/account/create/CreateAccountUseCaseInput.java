package com.github.leandrobove.mswallet.application.account.create;

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
