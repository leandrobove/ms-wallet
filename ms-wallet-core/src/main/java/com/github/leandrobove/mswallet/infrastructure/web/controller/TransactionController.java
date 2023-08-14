package com.github.leandrobove.mswallet.infrastructure.web.controller;

import com.github.leandrobove.mswallet.application.usecase.createTransaction.CreateTransactionUseCase;
import com.github.leandrobove.mswallet.application.usecase.createTransaction.CreateTransactionUseCaseInput;
import com.github.leandrobove.mswallet.infrastructure.web.model.CreateTransactionRequest;
import com.github.leandrobove.mswallet.infrastructure.web.model.CreateTransactionResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

    private final CreateTransactionUseCase createTransactionUseCase;

    public TransactionController(final CreateTransactionUseCase createTransactionUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTransactionResponse create(@RequestBody @Valid CreateTransactionRequest request) {

        var input = CreateTransactionUseCaseInput.from(
                request.getAccountFromId(),
                request.getAccountToId(),
                request.getAmount()
        );

        var output = createTransactionUseCase.execute(input);

        return new CreateTransactionResponse(
                output.getTransactionId(),
                output.getAccountIdFrom(),
                output.getAccountIdTo(),
                output.getAmount()
        );
    }
}
