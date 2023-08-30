package com.github.leandrobove.mswallet.application.usecase.createTransaction;

import com.github.leandrobove.mswallet.application.gateway.AccountGateway;
import com.github.leandrobove.mswallet.application.gateway.TransactionGateway;
import com.github.leandrobove.mswallet.domain.entity.Account;
import com.github.leandrobove.mswallet.domain.entity.Transaction;
import com.github.leandrobove.mswallet.domain.event.BalanceUpdatedEvent;
import com.github.leandrobove.mswallet.domain.event.TransactionCreatedEvent;
import com.github.leandrobove.mswallet.domain.exception.EntityNotFoundException;
import com.github.leandrobove.mswallet.infrastructure.event.dto.BalanceUpdatedEventPayload;
import com.github.leandrobove.mswallet.infrastructure.event.dto.TransactionCreatedEventPayload;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

@Service
public class CreateTransactionUseCase {
    private final TransactionGateway transactionGateway;
    private final AccountGateway accountGateway;
    private final ApplicationEventPublisher eventPublisher;

    public CreateTransactionUseCase(
            final TransactionGateway transactionGateway,
            final AccountGateway accountGateway,
            final ApplicationEventPublisher eventPublisher
    ) {
        this.transactionGateway = transactionGateway;
        this.accountGateway = accountGateway;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public CreateTransactionUseCaseOutput execute(final CreateTransactionUseCaseInput input) {
        this.validateInput(input);

        //find accountFrom
        Account accountFrom = this.findAccountOrFail(input.getAccountFromId());

        //find accountTo
        Account accountTo = this.findAccountOrFail(input.getAccountToId());

        //create transaction object
        Transaction transaction = Transaction.transfer(accountFrom, accountTo, input.getAmount());

        //update account balance
        accountGateway.updateBalance(accountFrom);
        accountGateway.updateBalance(accountTo);

        //persist object into the database
        transactionGateway.create(transaction);

        var balanceUpdatedEventPayload = new BalanceUpdatedEventPayload(
                accountFrom.getId().toString(),
                accountTo.getId().toString(),
                accountFrom.getBalance(),
                accountTo.getBalance()
        );

        var transactionCreatedEventPayload = new TransactionCreatedEventPayload(
                transaction.getId().toString(),
                accountFrom.getId().toString(),
                accountTo.getId().toString(),
                transaction.getAmount()
        );

        //publish events
        eventPublisher.publishEvent(new TransactionCreatedEvent(transactionCreatedEventPayload));
        eventPublisher.publishEvent(new BalanceUpdatedEvent(balanceUpdatedEventPayload));

        return CreateTransactionUseCaseOutput.from(transaction);
    }

    private Account findAccountOrFail(final String accountId) {
        return accountGateway.findById(accountId).orElseThrow(
                () -> new EntityNotFoundException(String.format("account id %s not found", accountId)));
    }

    private void validateInput(final CreateTransactionUseCaseInput input) {
        if (isNull(input.getAccountFromId()) || input.getAccountFromId() == "") {
            throw new IllegalArgumentException("account id from is required");
        }
        if (isNull(input.getAccountToId()) || input.getAccountToId() == "") {
            throw new IllegalArgumentException("account id to is required");
        }
    }
}
