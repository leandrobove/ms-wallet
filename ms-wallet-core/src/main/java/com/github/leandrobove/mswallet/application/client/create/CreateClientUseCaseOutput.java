package com.github.leandrobove.mswallet.application.client.create;

import com.github.leandrobove.mswallet.domain.client.Client;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Builder
@Getter
public class CreateClientUseCaseOutput {

    private String id;
    private String name;
    private String email;
    private String cpf;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static CreateClientUseCaseOutput from(final Client client) {
        return CreateClientUseCaseOutput.builder()
                .id(client.getId().value())
                .name(client.getName().fullName())
                .email(client.getEmail().value())
                .cpf(client.getCpf().value())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }
}
