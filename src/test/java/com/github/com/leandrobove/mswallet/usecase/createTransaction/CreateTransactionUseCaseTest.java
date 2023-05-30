package com.github.com.leandrobove.mswallet.usecase.createTransaction;

import com.github.com.leandrobove.mswallet.entity.Account;
import com.github.com.leandrobove.mswallet.entity.Client;
import com.github.com.leandrobove.mswallet.entity.Transaction;
import com.github.com.leandrobove.mswallet.event.BalanceUpdatedEvent;
import com.github.com.leandrobove.mswallet.event.TransactionCreatedEvent;
import com.github.com.leandrobove.mswallet.exception.EntityNotFoundException;
import com.github.com.leandrobove.mswallet.gateway.AccountGateway;
import com.github.com.leandrobove.mswallet.gateway.TransactionGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CreateTransactionUseCase useCase;

    @Test
    public void shouldCreateTransaction() {
        Client client1 = Client.create("John", "john@j.com");
        Client client2 = Client.create("Jack Doe", "jackdoe@j.com");

        Account accountFrom = Account.create(client1);
        Account accountTo = Account.create(client2);

        client1.addAccount(accountFrom);
        client2.addAccount(accountTo);

        accountFrom.credit(new BigDecimal(1000.00));
        accountTo.credit(new BigDecimal(1000.00));

        //mock
        when(accountGateway.find(accountFrom.getId().toString())).thenReturn(Optional.of(accountFrom));
        when(accountGateway.find(accountTo.getId().toString())).thenReturn(Optional.of(accountTo));
        doNothing().when(eventPublisher).publishEvent(any(TransactionCreatedEvent.class));
        doNothing().when(eventPublisher).publishEvent(any(BalanceUpdatedEvent.class));

        CreateTransactionUseCaseOutputDto output = useCase.execute(CreateTransactionUseCaseInputDto.builder()
                .accountFromId(accountFrom.getId().toString())
                .accountToId(accountTo.getId().toString())
                .amount(new BigDecimal(500.00))
                .build());

        verify(accountGateway, times(2)).find(anyString());
        verify(transactionGateway, times(1)).create(any(Transaction.class));
        verify(eventPublisher, times(1)).publishEvent(any(TransactionCreatedEvent.class));
        verify(eventPublisher, times(1)).publishEvent(any(BalanceUpdatedEvent.class));

        assertThat(output.getTransactionId()).isNotNull();
        assertThat(output.getAccountIdFrom()).isEqualTo(accountFrom.getId().toString());
        assertThat(output.getAccountIdTo()).isEqualTo(accountTo.getId().toString());
        assertThat(output.getAmount().compareTo(new BigDecimal(500.00))).isEqualTo(0);
        assertDoesNotThrow(() -> UUID.fromString(output.getTransactionId()));
    }

    @Test
    public void shouldNotCreateTransactionWhenAccountFromNotFound() {
        String accountId = "123";
        when(accountGateway.find(accountId)).thenThrow(new EntityNotFoundException(String.format("accountFrom id %s not found", accountId)));

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            CreateTransactionUseCaseOutputDto output = useCase.execute(CreateTransactionUseCaseInputDto.builder()
                    .accountFromId(accountId)
                    .accountToId(UUID.randomUUID().toString())
                    .amount(new BigDecimal(500.00))
                    .build());
        });

        assertThat(ex.getMessage()).isEqualTo("accountFrom id " + accountId + " not found");
        verify(accountGateway, times(1)).find(anyString());
        verify(transactionGateway, never()).create(any(Transaction.class));
        verify(eventPublisher, never()).publishEvent(any(TransactionCreatedEvent.class));
        verify(eventPublisher, never()).publishEvent(any(BalanceUpdatedEvent.class));
    }

    @Test
    public void shouldNotCreateTransactionWhenAccountToNotFound() {
        Client client1 = Client.create("John", "john@j.com");
        Account accountFrom = Account.create(client1);
        client1.addAccount(accountFrom);
        accountFrom.credit(new BigDecimal(1000.00));

        String accountToId = "123";
        when(accountGateway.find(accountFrom.getId().toString())).thenReturn(Optional.of(accountFrom));
        when(accountGateway.find(accountToId)).thenThrow(new EntityNotFoundException(String.format("accountTo id %s not found", accountToId)));

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            CreateTransactionUseCaseOutputDto output = useCase.execute(CreateTransactionUseCaseInputDto.builder()
                    .accountFromId(accountFrom.getId().toString())
                    .accountToId(accountToId)
                    .amount(new BigDecimal(500.00))
                    .build());
        });

        assertThat(ex.getMessage()).isEqualTo("accountTo id " + accountToId + " not found");
        verify(accountGateway, times(2)).find(anyString());
        verify(transactionGateway, never()).create(any(Transaction.class));
        verify(eventPublisher, never()).publishEvent(any(TransactionCreatedEvent.class));
        verify(eventPublisher, never()).publishEvent(any(BalanceUpdatedEvent.class));
    }

    @Test
    public void shouldNotCreateTransactionWhenAccountFromIsMissing() {
        assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(CreateTransactionUseCaseInputDto.builder()
                    .accountFromId("")
                    .accountToId(UUID.randomUUID().toString())
                    .amount(new BigDecimal(500.00))
                    .build());
        });
    }

    @Test
    public void shouldNotCreateTransactionWhenAccountToIsMissing() {
        assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(CreateTransactionUseCaseInputDto.builder()
                    .accountFromId(UUID.randomUUID().toString())
                    .accountToId("")
                    .amount(new BigDecimal(500.00))
                    .build());
        });
    }

    @Test
    public void shouldNotCreateTransactionWhenAmountIsMissing() {
        Client client1 = Client.create("John", "john@j.com");
        Client client2 = Client.create("Jack Doe", "jackdoe@j.com");

        Account accountFrom = Account.create(client1);
        Account accountTo = Account.create(client2);

        client1.addAccount(accountFrom);
        client2.addAccount(accountTo);

        accountFrom.credit(new BigDecimal(1000.00));
        accountTo.credit(new BigDecimal(1000.00));

        //mock
        when(accountGateway.find(accountFrom.getId().toString())).thenReturn(Optional.of(accountFrom));
        when(accountGateway.find(accountTo.getId().toString())).thenReturn(Optional.of(accountTo));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(CreateTransactionUseCaseInputDto.builder()
                    .accountFromId(accountFrom.getId().toString())
                    .accountToId(accountTo.getId().toString())
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
        Client client1 = Client.create("John", "john@j.com");
        Client client2 = Client.create("Jack Doe", "jackdoe@j.com");

        Account accountFrom = Account.create(client1);
        Account accountTo = Account.create(client2);

        client1.addAccount(accountFrom);
        client2.addAccount(accountTo);

        accountFrom.credit(new BigDecimal(1000.00));
        accountTo.credit(new BigDecimal(1000.00));

        //mock
        when(accountGateway.find(accountFrom.getId().toString())).thenReturn(Optional.of(accountFrom));
        when(accountGateway.find(accountTo.getId().toString())).thenReturn(Optional.of(accountTo));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            useCase.execute(CreateTransactionUseCaseInputDto.builder()
                    .accountFromId(accountFrom.getId().toString())
                    .accountToId(accountTo.getId().toString())
                    .amount(new BigDecimal(0.00))
                    .build());
        });
        assertThat(ex.getMessage()).isEqualTo("amount must be greater than zero");

        verify(transactionGateway, never()).create(any(Transaction.class));
        verify(eventPublisher, never()).publishEvent(any(TransactionCreatedEvent.class));
        verify(eventPublisher, never()).publishEvent(any(BalanceUpdatedEvent.class));
    }
}
