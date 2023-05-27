package com.github.com.leandrobove.mswallet.web;

import com.github.com.leandrobove.mswallet.usecase.createClient.CreateClientUseCase;
import com.github.com.leandrobove.mswallet.usecase.createClient.CreateClientUseCaseInputDto;
import com.github.com.leandrobove.mswallet.usecase.createClient.CreateClientUseCaseOutputDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {
    private final CreateClientUseCase createClientUseCase;

    public ClientController(CreateClientUseCase createClientUseCase) {
        this.createClientUseCase = createClientUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateClientUseCaseOutputDto create(@RequestBody CreateClientUseCaseInputDto input) {
        return createClientUseCase.execute(input);
    }
}