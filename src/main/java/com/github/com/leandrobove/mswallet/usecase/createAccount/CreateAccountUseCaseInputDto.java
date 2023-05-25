package com.github.com.leandrobove.mswallet.usecase.createAccount;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateAccountUseCaseInputDto {
    private String clientId;
}
