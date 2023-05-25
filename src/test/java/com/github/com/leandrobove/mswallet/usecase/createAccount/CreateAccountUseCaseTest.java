package com.github.com.leandrobove.mswallet.usecase.createAccount;

import com.github.com.leandrobove.mswallet.entity.Account;
import com.github.com.leandrobove.mswallet.entity.Client;
import com.github.com.leandrobove.mswallet.exception.EntityNotFoundException;
import com.github.com.leandrobove.mswallet.gateway.AccountGateway;
import com.github.com.leandrobove.mswallet.gateway.ClientGateway;
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
        Client expectedClient = Client.create("John", "j@j.com");

        when(clientGateway.find(expectedClient.getId().toString())).thenReturn(Optional.of(expectedClient));

        CreateAccountUseCaseOutputDto output = useCase.execute(CreateAccountUseCaseInputDto.builder()
                .clientId(expectedClient.getId().toString())
                .build());

        verify(clientGateway, times(1)).find(expectedClient.getId().toString());
        verify(accountGateway, times(1)).save(any(Account.class));

        assertThat(output.getId()).isNotNull();
    }

    @Test
    public void shouldNotCreateAccountWhenClientNotFound() {
        String clientId = "123";

        when(clientGateway.find(clientId)).thenThrow(new EntityNotFoundException(String.format("client id %s not found", clientId)));

        EntityNotFoundException ex = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            CreateAccountUseCaseOutputDto output = useCase.execute(CreateAccountUseCaseInputDto.builder()
                    .clientId(clientId)
                    .build());
        });
        assertThat(ex.getMessage()).isEqualTo("client id " + clientId + " not found");
        verify(clientGateway, times(1)).find(clientId);
        verify(accountGateway, never()).save(any(Account.class));
    }
}
