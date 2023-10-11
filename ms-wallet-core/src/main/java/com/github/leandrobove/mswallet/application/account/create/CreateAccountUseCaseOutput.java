package com.github.leandrobove.mswallet.application.account.create;

import com.github.leandrobove.mswallet.domain.account.Account;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateAccountUseCaseOutput {
    private String id;

    public static CreateAccountUseCaseOutput from(final Account account) {
        return CreateAccountUseCaseOutput.builder()
                .id(account.getId().value())
                .build();
    }
}
