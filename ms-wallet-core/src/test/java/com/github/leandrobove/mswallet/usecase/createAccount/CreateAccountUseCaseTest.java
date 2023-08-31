package com.github.leandrobove.mswallet.usecase.createAccount;

import com.github.leandrobove.mswallet.application.gateway.AccountGateway;
import com.github.leandrobove.mswallet.application.gateway.ClientGateway;
import com.github.leandrobove.mswallet.application.usecase.createAccount.CreateAccountUseCase;
import com.github.leandrobove.mswallet.application.usecase.createAccount.CreateAccountUseCaseInput;
import com.github.leandrobove.mswallet.application.usecase.createAccount.CreateAccountUseCaseOutput;
import com.github.leandrobove.mswallet.domain.entity.Account;
import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.entity.ClientId;
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

    @InjectMocks
    private CreateAccountUseCase useCase;

    @Test
    public void shouldCreateAccountUseCase() {
        Client expectedClient = Client.create("John", "john@gmail.com");
        ClientId clientId = expectedClient.getId();

        when(clientGateway.findById(clientId)).thenReturn(Optional.of(expectedClient));

        var input = CreateAccountUseCaseInput.builder()
                .clientId(clientId.value())
                .build();

        CreateAccountUseCaseOutput output = useCase.execute(input);

        verify(clientGateway, times(1)).findById(clientId);
        verify(accountGateway, times(1)).save(any(Account.class));

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
