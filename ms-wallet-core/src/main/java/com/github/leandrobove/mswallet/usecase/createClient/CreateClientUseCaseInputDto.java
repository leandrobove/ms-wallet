package com.github.leandrobove.mswallet.usecase.createClient;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateClientUseCaseInputDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

}
