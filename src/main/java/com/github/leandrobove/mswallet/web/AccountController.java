package com.github.leandrobove.mswallet.web;

import com.github.leandrobove.mswallet.usecase.createAccount.CreateAccountUseCase;
import com.github.leandrobove.mswallet.usecase.createAccount.CreateAccountUseCaseInputDto;
import com.github.leandrobove.mswallet.usecase.createAccount.CreateAccountUseCaseOutputDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;

    public AccountController(CreateAccountUseCase createAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateAccountUseCaseOutputDto create(@RequestBody @Valid CreateAccountUseCaseInputDto input) {
        return createAccountUseCase.execute(input);
    }
}
