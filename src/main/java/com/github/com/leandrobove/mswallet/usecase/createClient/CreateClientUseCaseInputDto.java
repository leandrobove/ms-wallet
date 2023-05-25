package com.github.com.leandrobove.mswallet.usecase.createClient;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateClientUseCaseInputDto {

    private String name;
    private String email;

}
