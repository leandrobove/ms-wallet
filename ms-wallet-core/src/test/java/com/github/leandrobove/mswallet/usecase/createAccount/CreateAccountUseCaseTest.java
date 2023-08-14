package com.github.leandrobove.mswallet.usecase.createAccount;

import com.github.leandrobove.mswallet.application.gateway.AccountGateway;
import com.github.leandrobove.mswallet.application.gateway.ClientGateway;
import com.github.leandrobove.mswallet.application.usecase.createAccount.CreateAccountUseCase;
import com.github.leandrobove.mswallet.application.usecase.createAccount.CreateAccountUseCaseInput;
import com.github.leandrobove.mswallet.application.usecase.createAccount.CreateAccountUseCaseOutput;
import com.github.leandrobove.mswallet.domain.entity.Account;
import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.exception.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateAccountUseCaseTest {

    @Mock
    private ClientGateway clientGateway;

    @Mock
    private AccountGateway accountGateway;

    @InjectMocks
    private CreateAccountUseCase useCase;

    @Test
    public void shouldCreateAccountUseCase() {
        String clientId = UUID.randomUUID().toString();
        Client expectedClient = Client.createWithId(clientId, "John", "j@j.com");

        when(clientGateway.findById(clientId)).thenReturn(Optional.of(expectedClient));

        CreateAccountUseCaseOutput output = useCase.execute(CreateAccountUseCaseInput.builder()
                .clientId(clientId)
                .build());

        verify(clientGateway, times(1)).findById(clientId);
        verify(accountGateway, times(1)).save(any(Account.class));

        assertThat(output.getId()).isNotNull();
        assertDoesNotThrow(() -> UUID.fromString(output.getId()));
    }

    @Test
    public void shouldNotCreateAccountWhenClientNotFound() {
        String clientId = "123";

        when(clientGateway.findById(clientId)).thenThrow(new EntityNotFoundException(String.format("client id %s not found", clientId)));

        EntityNotFoundException ex = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            CreateAccountUseCaseOutput output = useCase.execute(CreateAccountUseCaseInput.builder()
                    .clientId(clientId)
                    .build());
        });
        assertThat(ex.getMessage()).isEqualTo("client id " + clientId + " not found");
        verify(clientGateway, times(1)).findById(clientId);
        verify(accountGateway, never()).save(any(Account.class));
    }

    @Test
    public void shouldNotCreateAccountWhenClientIdIsMissing() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CreateAccountUseCaseOutput output = useCase.execute(CreateAccountUseCaseInput.builder()
                    .clientId("")
                    .build());
        });
    }
}
