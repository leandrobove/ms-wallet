package com.github.leandrobove.mswallet.infrastructure.web.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record CreateClientResponse(
        String id,
        String name,
        String email,
        String cpf,
        @JsonProperty("created_at") OffsetDateTime createdAt,
        @JsonProperty("updated_at") OffsetDateTime updatedAt
) {
}
