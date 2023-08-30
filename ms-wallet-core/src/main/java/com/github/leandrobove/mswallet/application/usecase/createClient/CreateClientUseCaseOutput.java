package com.github.leandrobove.mswallet.application.usecase.createClient;

import com.github.leandrobove.mswallet.domain.entity.Client;
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

    public static CreateClientUseCaseOutput from(final Client client) {
        return CreateClientUseCaseOutput.builder()
                .id(client.getId().value())
                .name(client.getName())
                .email(client.getEmail())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }
}
