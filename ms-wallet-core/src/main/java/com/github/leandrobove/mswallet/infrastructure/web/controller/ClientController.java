package com.github.leandrobove.mswallet.infrastructure.web.controller;

import com.github.leandrobove.mswallet.application.usecase.createClient.CreateClientUseCase;
import com.github.leandrobove.mswallet.application.usecase.createClient.CreateClientUseCaseInput;
import com.github.leandrobove.mswallet.infrastructure.web.model.CreateClientRequest;
import com.github.leandrobove.mswallet.infrastructure.web.model.CreateClientResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {
    private final CreateClientUseCase createClientUseCase;

    public ClientController(final CreateClientUseCase createClientUseCase) {
        this.createClientUseCase = createClientUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateClientResponse create(@RequestBody @Valid CreateClientRequest createClientRequest) {

        var input = CreateClientUseCaseInput.from(
                createClientRequest.getName(),
                createClientRequest.getEmail()
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
