package com.github.leandrobove.mswallet.application.account.create;

import com.github.leandrobove.mswallet.application.account.create.CreateAccountUseCase;
import com.github.leandrobove.mswallet.application.account.create.CreateAccountUseCaseInput;
import com.github.leandrobove.mswallet.application.account.create.CreateAccountUseCaseOutput;
import com.github.leandrobove.mswallet.domain.account.AccountGateway;
import com.github.leandrobove.mswallet.domain.client.ClientGateway;
import com.github.leandrobove.mswallet.domain.DomainEvent;
import com.github.leandrobove.mswallet.domain.EventPublisher;
import com.github.leandrobove.mswallet.domain.account.Account;
import com.github.leandrobove.mswallet.domain.client.Client;
import com.github.leandrobove.mswallet.domain.client.ClientId;
import com.github.leandrobove.mswallet.domain.exception.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateAccountUseCaseTest {

    @Mock
    private ClientGateway clientGateway;

    @Mock
    private AccountGateway accountGateway;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private CreateAccountUseCase useCase;

    @Test
    public void shouldCreateAccountUseCase() {
        Client expectedClient = Client.create("John","Brad",  "john@gmail.com", "297.263.110-20");
        ClientId clientId = expectedClient.getId();

        when(clientGateway.findById(clientId)).thenReturn(Optional.of(expectedClient));

        var input = CreateAccountUseCaseInput.builder()
                .clientId(clientId.value())
                .build();

        CreateAccountUseCaseOutput output = useCase.execute(input);

        verify(clientGateway, times(1)).findById(clientId);
        verify(accountGateway, times(1)).save(any(Account.class));
        verify(eventPublisher, times(1)).publishEvent(any(DomainEvent.class));

        assertThat(output.getId()).isNotNull();
    }

    @Test
    public void shouldNotCreateAccountWhenClientNotFound() {
        var clientId = ClientId.unique();

        when(clientGateway.findById(clientId)).thenThrow(new EntityNotFoundException(String.format("client id %s not found", clientId)));

        EntityNotFoundException ex = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            CreateAccountUseCaseOutput output = useCase.execute(CreateAccountUseCaseInput.builder()
                    .clientId(clientId.value())
                    .build());
        });
        assertThat(ex.getMessage()).isEqualTo("client id " + clientId + " not found");
        verify(clientGateway, times(1)).findById(clientId);
        verify(accountGateway, never()).save(any(Account.class));
        verify(eventPublisher, never()).publishEvent(any(DomainEvent.class));
    }

    @Test
    public void shouldNotCreateAccountWhenClientIdIsMissing() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CreateAccountUseCaseOutput output = useCase.execute(CreateAccountUseCaseInput.builder()
                    .clientId("")
                    .build());
        });
        verify(accountGateway, never()).save(any(Account.class));
        verify(eventPublisher, never()).publishEvent(any(DomainEvent.class));
    }
}
