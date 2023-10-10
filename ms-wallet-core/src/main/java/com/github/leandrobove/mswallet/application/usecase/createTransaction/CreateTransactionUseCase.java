package com.github.leandrobove.mswallet.application.usecase.createTransaction;

import com.github.leandrobove.mswallet.application.gateway.AccountGateway;
import com.github.leandrobove.mswallet.application.gateway.TransactionGateway;
import com.github.leandrobove.mswallet.domain.EventPublisher;
import com.github.leandrobove.mswallet.domain.entity.Account;
import com.github.leandrobove.mswallet.domain.entity.AccountId;
import com.github.leandrobove.mswallet.domain.entity.Money;
import com.github.leandrobove.mswallet.domain.entity.Transaction;
import com.github.leandrobove.mswallet.domain.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

@Service
public class CreateTransactionUseCase {
    private final TransactionGateway transactionGateway;
    private final AccountGateway accountGateway;
    private final EventPublisher eventPublisher;

    public CreateTransactionUseCase(
            final TransactionGateway transactionGateway,
            final AccountGateway accountGateway,
            final EventPublisher eventPublisher
    ) {
        this.transactionGateway = transactionGateway;
        this.accountGateway = accountGateway;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public CreateTransactionUseCaseOutput execute(final CreateTransactionUseCaseInput input) {
        this.validateInput(input);

        final var amount = Money.from(input.getAmount());

        //find accountFrom
        Account accountFrom = this.findAccountOrFail(input.getAccountFromId());

        //find accountTo
        Account accountTo = this.findAccountOrFail(input.getAccountToId());

        //create transaction object
        Transaction transaction = Transaction.transfer(accountFrom, accountTo, amount);

        //update account balance
        accountGateway.updateBalance(accountFrom);
        accountGateway.updateBalance(accountTo);

        //persist object into the database
        transactionGateway.create(transaction);

        //publish events
        transaction.publishDomainEvents(eventPublisher);

        return CreateTransactionUseCaseOutput.from(transaction);
    }

    private Account findAccountOrFail(final String accountId) {
        return accountGateway.findById(AccountId.from(accountId)).orElseThrow(
                () -> new EntityNotFoundException(String.format("account id %s not found", accountId)));
    }

    private void validateInput(final CreateTransactionUseCaseInput input) {
        if (isNull(input.getAccountFromId()) || input.getAccountFromId().isBlank()) {
            throw new IllegalArgumentException("account id from is required");
        }
        if (isNull(input.getAccountToId()) || input.getAccountToId().isBlank()) {
            throw new IllegalArgumentException("account id to is required");
        }
    }
}
