package com.github.leandrobove.mswallet.application.usecase.createClient;

import com.github.leandrobove.mswallet.application.gateway.ClientGateway;
import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.exception.EmailAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

@Service
public class CreateClientUseCase {
    private final ClientGateway clientGateway;

    public CreateClientUseCase(final ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    @Transactional
    public CreateClientUseCaseOutput execute(CreateClientUseCaseInput input) {
        this.validateInput(input);

        var name = input.getName();
        var email = input.getEmail();

        this.verifyEmailAlreadyExists(email);

        Client client = Client.create(name, email);

        clientGateway.save(client);

        return CreateClientUseCaseOutput.builder()
                .id(client.getId().toString())
                .name(client.getName())
                .email(client.getEmail())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }

    private void verifyEmailAlreadyExists(String email) {
        //verify whether email already exists
        if (clientGateway.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException(String.format("email %s already exists", email));
        }
    }

    private void validateInput(CreateClientUseCaseInput input) {
        if (isNull(input.getEmail()) || input.getEmail() == "") {
            throw new IllegalArgumentException("email is required");
        }
    }
}
