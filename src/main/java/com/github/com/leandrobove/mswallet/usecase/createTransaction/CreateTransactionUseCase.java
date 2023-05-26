package com.github.com.leandrobove.mswallet.usecase.createTransaction;

import com.github.com.leandrobove.mswallet.entity.Account;
import com.github.com.leandrobove.mswallet.entity.Transaction;
import com.github.com.leandrobove.mswallet.exception.EntityNotFoundException;
import com.github.com.leandrobove.mswallet.gateway.AccountGateway;
import com.github.com.leandrobove.mswallet.gateway.TransactionGateway;

public class CreateTransactionUseCase {
    private final TransactionGateway transactionGateway;
    private final AccountGateway accountGateway;

    public CreateTransactionUseCase(TransactionGateway transactionGateway, AccountGateway accountGateway) {
        this.transactionGateway = transactionGateway;
        this.accountGateway = accountGateway;
    }

    public CreateTransactionUseCaseOutputDto execute(CreateTransactionUseCaseInputDto input) throws EntityNotFoundException {
        //find accountFromById
        Account accountFrom = accountGateway.find(input.getAccountFromId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("accountFrom id %s not found", input.getAccountFromId())));

        //find accountToById
        Account accountTo = accountGateway.find(input.getAccountToId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("accountTo id %s not found", input.getAccountToId())));

        //create transaction object
        Transaction transaction = new Transaction(accountFrom, accountTo, input.getAmount());

        //persist object into the database
        transactionGateway.create(transaction);

        return CreateTransactionUseCaseOutputDto.builder()
                .transactionId(transaction.getId().toString())
                .build();
    }
}
