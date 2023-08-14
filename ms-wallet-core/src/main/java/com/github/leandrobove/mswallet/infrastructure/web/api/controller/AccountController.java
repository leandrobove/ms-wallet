package com.github.leandrobove.mswallet.infrastructure.web.api.controller;

import com.github.leandrobove.mswallet.application.usecase.createAccount.CreateAccountUseCase;
import com.github.leandrobove.mswallet.application.usecase.createAccount.CreateAccountUseCaseInput;
import com.github.leandrobove.mswallet.infrastructure.web.api.AccountAPI;
import com.github.leandrobove.mswallet.infrastructure.web.api.model.CreateAccountRequest;
import com.github.leandrobove.mswallet.infrastructure.web.api.model.CreateAccountResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController implements AccountAPI {

    private final CreateAccountUseCase createAccountUseCase;

    public AccountController(final CreateAccountUseCase createAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
    }

    @Override
    public CreateAccountResponse create(CreateAccountRequest createAccountRequest) {

        var input = CreateAccountUseCaseInput.from(createAccountRequest.getClientId());

        var output = createAccountUseCase.execute(input);

        return new CreateAccountResponse(output.getId());
    }
}
