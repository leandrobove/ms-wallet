package com.github.leandrobove.mswallet.web;

import com.github.leandrobove.mswallet.usecase.createClient.CreateClientUseCase;
import com.github.leandrobove.mswallet.usecase.createClient.CreateClientUseCaseInputDto;
import com.github.leandrobove.mswallet.usecase.createClient.CreateClientUseCaseOutputDto;
import jakarta.validation.Valid;
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
    public CreateClientUseCaseOutputDto create(@RequestBody @Valid CreateClientUseCaseInputDto input) {
        return createClientUseCase.execute(input);
    }
}
