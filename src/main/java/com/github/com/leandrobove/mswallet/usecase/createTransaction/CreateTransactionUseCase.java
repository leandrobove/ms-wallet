package com.github.com.leandrobove.mswallet.usecase.createTransaction;

import com.github.com.leandrobove.mswallet.entity.Account;
import com.github.com.leandrobove.mswallet.entity.Transaction;
import com.github.com.leandrobove.mswallet.event.BalanceUpdatedEvent;
import com.github.com.leandrobove.mswallet.event.TransactionCreatedEvent;
import com.github.com.leandrobove.mswallet.event.dto.BalanceUpdatedEventPayload;
import com.github.com.leandrobove.mswallet.event.dto.TransactionCreatedEventPayload;
import com.github.com.leandrobove.mswallet.exception.EntityNotFoundException;
import com.github.com.leandrobove.mswallet.gateway.AccountGateway;
import com.github.com.leandrobove.mswallet.gateway.TransactionGateway;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

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
        this.validateInput(input);

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
                .accountIdFrom(accountFrom.getId().toString())
                .accountIdTo(accountTo.getId().toString())
                .amount(transaction.getAmount())
                .build();

        var balanceUpdatedEventPayload = new BalanceUpdatedEventPayload(accountFrom.getId().toString(),
                accountTo.getId().toString(), accountFrom.getBalance(), accountTo.getBalance());
        var transactionCreatedEventPayload = new TransactionCreatedEventPayload(transaction.getId().toString(),
                accountFrom.getId().toString(), accountTo.getId().toString(), transaction.getAmount());

        //publish events
        eventPublisher.publishEvent(new TransactionCreatedEvent(transactionCreatedEventPayload));
        eventPublisher.publishEvent(new BalanceUpdatedEvent(balanceUpdatedEventPayload));

        return output;
    }

    private void validateInput(CreateTransactionUseCaseInputDto input) {
        if (isNull(input.getAccountFromId()) || input.getAccountFromId() == "") {
            throw new IllegalArgumentException("account id from is required");
        }
        if (isNull(input.getAccountToId()) || input.getAccountToId() == "") {
            throw new IllegalArgumentException("account id to is required");
        }
    }
}
