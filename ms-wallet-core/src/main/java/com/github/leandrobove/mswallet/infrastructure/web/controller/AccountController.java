package com.github.leandrobove.mswallet.infrastructure.web.controller;

import com.github.leandrobove.mswallet.application.usecase.createAccount.CreateAccountUseCase;
import com.github.leandrobove.mswallet.application.usecase.createAccount.CreateAccountUseCaseInput;
import com.github.leandrobove.mswallet.infrastructure.web.model.CreateAccountRequest;
import com.github.leandrobove.mswallet.infrastructure.web.model.CreateAccountResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;

    public AccountController(final CreateAccountUseCase createAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAccountResponse create(@RequestBody @Valid CreateAccountRequest createAccountRequest) {

        var input = CreateAccountUseCaseInput.from(createAccountRequest.getClientId());

        var output = createAccountUseCase.execute(input);

        return new CreateAccountResponse(output.getId());
    }
}
