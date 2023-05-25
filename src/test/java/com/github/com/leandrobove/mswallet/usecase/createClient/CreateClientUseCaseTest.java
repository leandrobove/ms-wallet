package com.github.com.leandrobove.mswallet.usecase.createClient;

import com.github.com.leandrobove.mswallet.entity.Client;
import com.github.com.leandrobove.mswallet.gateway.ClientGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateClientUseCaseTest {

    @Mock
    private ClientGateway clientGateway;

    @InjectMocks
    private CreateClientUseCase useCase;

    @Test
    public void shouldCreateClientUseCase() {
        CreateClientUseCaseOutputDto output = useCase.execute(CreateClientUseCaseInputDto.builder()
                .name("John")
                .email("j@j.com")
                .build());

        verify(clientGateway, times(1)).save(any(Client.class));

        assertThat(output.getId()).isNotNull();
        assertThat(output.getName()).isEqualTo("John");
        assertThat(output.getEmail()).isEqualTo("j@j.com");
        assertThat(output.getCreatedAt()).isNotNull();
        assertThat(output.getUpdatedAt()).isNotNull();
    }

}
