package com.github.leandrobove.mswallet.infrastructure.web.api.controller;

import com.github.leandrobove.mswallet.application.client.create.CreateClientUseCase;
import com.github.leandrobove.mswallet.application.client.create.CreateClientUseCaseInput;
import com.github.leandrobove.mswallet.domain.client.CPF;
import com.github.leandrobove.mswallet.infrastructure.web.api.ClientAPI;
import com.github.leandrobove.mswallet.infrastructure.web.api.model.CreateClientRequest;
import com.github.leandrobove.mswallet.infrastructure.web.api.model.CreateClientResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController implements ClientAPI {
    private final CreateClientUseCase createClientUseCase;

    public ClientController(final CreateClientUseCase createClientUseCase) {
        this.createClientUseCase = createClientUseCase;
    }

    @Override
    public CreateClientResponse create(CreateClientRequest request) {

        var input = CreateClientUseCaseInput.from(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getCpf()
        );

        var output = createClientUseCase.execute(input);

        return new CreateClientResponse(
                output.getId(),
                output.getName(),
                output.getEmail(),
                CPF.from(output.getCpf()).format(),
                output.getCreatedAt(),
                output.getUpdatedAt()
        );
    }
}
