package com.github.leandrobove.mswallet.web;

import com.github.leandrobove.mswallet.usecase.createTransaction.CreateTransactionUseCase;
import com.github.leandrobove.mswallet.usecase.createTransaction.CreateTransactionUseCaseInputDto;
import com.github.leandrobove.mswallet.usecase.createTransaction.CreateTransactionUseCaseOutputDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

    private final CreateTransactionUseCase createTransactionUseCase;

    public TransactionController(CreateTransactionUseCase createTransactionUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTransactionUseCaseOutputDto create(@RequestBody @Valid CreateTransactionUseCaseInputDto input) {
        return createTransactionUseCase.execute(input);
    }
}
