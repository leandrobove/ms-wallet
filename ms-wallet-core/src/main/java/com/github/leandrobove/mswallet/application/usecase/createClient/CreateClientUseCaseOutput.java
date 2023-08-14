package com.github.leandrobove.mswallet.application.usecase.createClient;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Builder
@Getter
public class CreateClientUseCaseOutput {

    private String id;
    private String name;
    private String email;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
