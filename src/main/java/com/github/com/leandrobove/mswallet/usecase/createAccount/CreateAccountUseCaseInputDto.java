package com.github.com.leandrobove.mswallet.usecase.createAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CreateAccountUseCaseInputDto {
    private String clientId;
}
