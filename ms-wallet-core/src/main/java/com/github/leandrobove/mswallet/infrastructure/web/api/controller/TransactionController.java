package com.github.leandrobove.mswallet.infrastructure.web.api.controller;

import com.github.leandrobove.mswallet.application.transaction.create.CreateTransactionUseCase;
import com.github.leandrobove.mswallet.application.transaction.create.CreateTransactionUseCaseInput;
import com.github.leandrobove.mswallet.infrastructure.web.api.TransactionAPI;
import com.github.leandrobove.mswallet.infrastructure.web.api.model.CreateTransactionRequest;
import com.github.leandrobove.mswallet.infrastructure.web.api.model.CreateTransactionResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController implements TransactionAPI {

    private final CreateTransactionUseCase createTransactionUseCase;

    public TransactionController(final CreateTransactionUseCase createTransactionUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
    }

    @Override
    public CreateTransactionResponse create(CreateTransactionRequest request) {

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
