package com.github.leandrobove.mswallet.infrastructure.web.api.controller;

import com.github.leandrobove.mswallet.application.usecase.createClient.CreateClientUseCase;
import com.github.leandrobove.mswallet.application.usecase.createClient.CreateClientUseCaseInput;
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
                request.getName(),
                request.getEmail()
        );

        var output = createClientUseCase.execute(input);

        return new CreateClientResponse(
                output.getId(),
                output.getName(),
                output.getEmail(),
                output.getCreatedAt(),
                output.getUpdatedAt()
        );
    }
}
