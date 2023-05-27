package com.github.com.leandrobove.mswallet.usecase.createClient;

import com.github.com.leandrobove.mswallet.entity.Client;
import com.github.com.leandrobove.mswallet.gateway.ClientGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateClientUseCase {
    private final ClientGateway clientGateway;

    public CreateClientUseCase(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    @Transactional
    public CreateClientUseCaseOutputDto execute(CreateClientUseCaseInputDto input) {
        Client client = Client.create(input.getName(), input.getEmail());

        clientGateway.save(client);

        return CreateClientUseCaseOutputDto.builder()
                .id(client.getId().toString())
                .name(client.getName())
                .email(client.getEmail())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }
}
