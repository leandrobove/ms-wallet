package com.github.com.leandrobove.mswallet.usecase.createAccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CreateAccountUseCaseInputDto {

    @JsonProperty("client_id")
    private String clientId;
}
