package com.github.com.leandrobove.mswallet.usecase.createTransaction;

import com.github.com.leandrobove.mswallet.entity.Account;
import com.github.com.leandrobove.mswallet.entity.Transaction;
import com.github.com.leandrobove.mswallet.event.TransactionCreatedEvent;
import com.github.com.leandrobove.mswallet.exception.EntityNotFoundException;
import com.github.com.leandrobove.mswallet.gateway.AccountGateway;
import com.github.com.leandrobove.mswallet.gateway.TransactionGateway;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateTransactionUseCase {
    private final TransactionGateway transactionGateway;
    private final AccountGateway accountGateway;
    private final ApplicationEventPublisher eventPublisher;

    public CreateTransactionUseCase(TransactionGateway transactionGateway, AccountGateway accountGateway, ApplicationEventPublisher eventPublisher) {
        this.transactionGateway = transactionGateway;
        this.accountGateway = accountGateway;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public CreateTransactionUseCaseOutputDto execute(CreateTransactionUseCaseInputDto input) throws EntityNotFoundException {
        //find accountFromById
        Account accountFrom = accountGateway.find(input.getAccountFromId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("accountFrom id %s not found", input.getAccountFromId())));

        //find accountToById
        Account accountTo = accountGateway.find(input.getAccountToId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("accountTo id %s not found", input.getAccountToId())));

        //create transaction object
        Transaction transaction = new Transaction(accountFrom, accountTo, input.getAmount());

        //update account balance
        accountGateway.updateBalance(accountFrom);
        accountGateway.updateBalance(accountTo);

        //persist object into the database
        transactionGateway.create(transaction);

        CreateTransactionUseCaseOutputDto output = CreateTransactionUseCaseOutputDto.builder()
                .transactionId(transaction.getId().toString())
                .build();

        //publish event
        eventPublisher.publishEvent(new TransactionCreatedEvent(this, output));

        return output;
    }
}
