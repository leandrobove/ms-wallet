package com.github.leandrobove.mswallet.application.usecase.createTransaction;

import com.github.leandrobove.mswallet.application.gateway.AccountGateway;
import com.github.leandrobove.mswallet.application.gateway.TransactionGateway;
import com.github.leandrobove.mswallet.application.usecase.createTransaction.CreateTransactionUseCase;
import com.github.leandrobove.mswallet.application.usecase.createTransaction.CreateTransactionUseCaseInput;
import com.github.leandrobove.mswallet.application.usecase.createTransaction.CreateTransactionUseCaseOutput;
import com.github.leandrobove.mswallet.domain.EventPublisher;
import com.github.leandrobove.mswallet.domain.entity.Account;
import com.github.leandrobove.mswallet.domain.entity.AccountId;
import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.entity.Transaction;
import com.github.leandrobove.mswallet.domain.event.BalanceUpdatedEvent;
import com.github.leandrobove.mswallet.domain.event.TransactionCreatedEvent;
import com.github.leandrobove.mswallet.domain.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateTransactionUseCaseTest {

    @Mock
    private TransactionGateway transactionGateway;

    @Mock
    private AccountGateway accountGateway;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private CreateTransactionUseCase useCase;

    @Test
    public void shouldCreateTransaction() {
        Client client1 = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        Client client2 = Client.create("Jack", "Doe", "jackdoe@gmail.com", "152.163.310-00");

        Account accountFrom = Account.create(client1);
        Account accountTo = Account.create(client2);

        client1.addAccount(accountFrom);
        client2.addAccount(accountTo);

        accountFrom.credit(new BigDecimal(1000.00));
        accountTo.credit(new BigDecimal(1000.00));

        //mock
        when(accountGateway.findById(accountFrom.getId())).thenReturn(Optional.of(accountFrom));
        when(accountGateway.findById(accountTo.getId())).thenReturn(Optional.of(accountTo));
//        doNothing().when(eventPublisher).publishEvent(any(TransactionCreatedEvent.class));
//        doNothing().when(eventPublisher).publishEvent(any(BalanceUpdatedEvent.class));

        CreateTransactionUseCaseOutput output = useCase.execute(CreateTransactionUseCaseInput.builder()
                .accountFromId(accountFrom.getId().value())
                .accountToId(accountTo.getId().value())
                .amount(new BigDecimal(500.00))
                .build());

        verify(accountGateway, times(2)).findById(any(AccountId.class));
        verify(transactionGateway, times(1)).create(any(Transaction.class));
        verify(eventPublisher, times(1)).publishEvent(any(TransactionCreatedEvent.class));
        verify(eventPublisher, times(1)).publishEvent(any(BalanceUpdatedEvent.class));

        assertThat(output.getTransactionId()).isNotNull();
        assertThat(output.getAccountIdFrom()).isEqualTo(accountFrom.getId().value());
        assertThat(output.getAccountIdTo()).isEqualTo(accountTo.getId().value());
        assertThat(output.getAmount().compareTo(new BigDecimal(500.00))).isEqualTo(0);
    }

    @Test
    public void shouldNotCreateTransactionWhenAccountFromNotFound() {
        var accountId = AccountId.unique();
        when(accountGateway.findById(accountId)).thenThrow(new EntityNotFoundException(String.format("accountFrom id %s not found", accountId)));

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            CreateTransactionUseCaseOutput output = useCase.execute(CreateTransactionUseCaseInput.builder()
                    .accountFromId(accountId.value())
                    .accountToId(UUID.randomUUID().toString())
                    .amount(new BigDecimal(500.00))
                    .build());
        });

        assertThat(ex.getMessage()).isEqualTo("accountFrom id " + accountId + " not found");
        verify(accountGateway, times(1)).findById(any(AccountId.class));
        verify(transactionGateway, never()).create(any(Transaction.class));
        verify(eventPublisher, never()).publishEvent(any(TransactionCreatedEvent.class));
        verify(eventPublisher, never()).publishEvent(any(BalanceUpdatedEvent.class));
    }

    @Test
    public void shouldNotCreateTransactionWhenAccountToNotFound() {
        Client client1 = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        Account accountFrom = Account.create(client1);
        client1.addAccount(accountFrom);
        accountFrom.credit(new BigDecimal(1000.00));

        var accountToId = AccountId.unique();
        when(accountGateway.findById(accountFrom.getId())).thenReturn(Optional.of(accountFrom));
        when(accountGateway.findById(accountToId)).thenThrow(new EntityNotFoundException(String.format("accountTo id %s not found", accountToId)));

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            CreateTransactionUseCaseOutput output = useCase.execute(CreateTransactionUseCaseInput.builder()
                    .accountFromId(accountFrom.getId().value())
                    .accountToId(accountToId.value())
                    .amount(new BigDecimal(500.00))
                    .build());
        });

        assertThat(ex.getMessage()).isEqualTo("accountTo id " + accountToId + " not found");
        verify(accountGateway, times(2)).findById(any(AccountId.class));
        verify(transactionGateway, never()).create(any(Transaction.class));
        verify(eventPublisher, never()).publishEvent(any(TransactionCreatedEvent.class));
        verify(eventPublisher, never()).publishEvent(any(BalanceUpdatedEvent.class));
    }

    @Test
    public void shouldNotCreateTransactionWhenAccountFromIsMissing() {
        assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(CreateTransactionUseCaseInput.builder()
                    .accountFromId("")
                    .accountToId(UUID.randomUUID().toString())
                    .amount(new BigDecimal(500.00))
                    .build());
        });
    }

    @Test
    public void shouldNotCreateTransactionWhenAccountToIsMissing() {
        assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(CreateTransactionUseCaseInput.builder()
                    .accountFromId(UUID.randomUUID().toString())
                    .accountToId("")
                    .amount(new BigDecimal(500.00))
                    .build());
        });
    }

    @Test
    public void shouldNotCreateTransactionWhenAmountIsMissing() {
        Client client1 = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        Client client2 = Client.create("Jack", "Doe", "jackdoe@gmail.com", "152.163.310-00");

        Account accountFrom = Account.create(client1);
        Account accountTo = Account.create(client2);

        client1.addAccount(accountFrom);
        client2.addAccount(accountTo);

        accountFrom.credit(new BigDecimal(1000.00));
        accountTo.credit(new BigDecimal(1000.00));

        //mock
        when(accountGateway.findById(accountFrom.getId())).thenReturn(Optional.of(accountFrom));
        when(accountGateway.findById(accountTo.getId())).thenReturn(Optional.of(accountTo));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(CreateTransactionUseCaseInput.builder()
                    .accountFromId(accountFrom.getId().value())
                    .accountToId(accountTo.getId().value())
                    .amount(null)
                    .build());
        });
        assertThat(ex.getMessage()).isEqualTo("amount is required");

        verify(transactionGateway, never()).create(any(Transaction.class));
        verify(eventPublisher, never()).publishEvent(any(TransactionCreatedEvent.class));
        verify(eventPublisher, never()).publishEvent(any(BalanceUpdatedEvent.class));
    }

    @Test
    public void shouldNotCreateTransactionWhenAmountIsEqualToZero() {
        Client client1 = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        Client client2 = Client.create("Jack", "Doe", "jackdoe@gmail.com", "152.163.310-00");

        Account accountFrom = Account.create(client1);
        Account accountTo = Account.create(client2);

        client1.addAccount(accountFrom);
        client2.addAccount(accountTo);

        accountFrom.credit(new BigDecimal(1000.00));
        accountTo.credit(new BigDecimal(1000.00));

        //mock
        when(accountGateway.findById(accountFrom.getId())).thenReturn(Optional.of(accountFrom));
        when(accountGateway.findById(accountTo.getId())).thenReturn(Optional.of(accountTo));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(CreateTransactionUseCaseInput.builder()
                    .accountFromId(accountFrom.getId().value())
                    .accountToId(accountTo.getId().value())
                    .amount(new BigDecimal(0.00))
                    .build());
        });
        assertThat(ex.getMessage()).isEqualTo("amount must be greater than zero");

        verify(transactionGateway, never()).create(any(Transaction.class));
        verify(eventPublisher, never()).publishEvent(any(TransactionCreatedEvent.class));
        verify(eventPublisher, never()).publishEvent(any(BalanceUpdatedEvent.class));
    }
}
