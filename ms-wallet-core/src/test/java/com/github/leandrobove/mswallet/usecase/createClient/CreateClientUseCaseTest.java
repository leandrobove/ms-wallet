package com.github.leandrobove.mswallet.usecase.createClient;

import com.github.leandrobove.mswallet.entity.Client;
import com.github.leandrobove.mswallet.exception.EmailAlreadyExistsException;
import com.github.leandrobove.mswallet.gateway.ClientGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertDoesNotThrow(() -> UUID.fromString(output.getId()));
        assertThat(output.getName()).isEqualTo("John");
        assertThat(output.getEmail()).isEqualTo("j@j.com");
        assertThat(output.getCreatedAt()).isNotNull();
        assertThat(output.getUpdatedAt()).isNotNull();
    }

    @Test
    public void shouldNotCreateClientWhenEmailAlreadyExists() {
        String email = "j@j.com";
        when(clientGateway.findByEmail(email)).thenReturn(Optional.of(Client.create("John", "j@j.com")));

        assertThrows(EmailAlreadyExistsException.class, () -> {
            var output = useCase.execute(CreateClientUseCaseInputDto.builder()
                    .name("John")
                    .email("j@j.com")
                    .build());
        });
    }

    @Test
    public void shouldNotCreateClientWhenNameIsMissing() {
        String email = "j@j.com";
        when(clientGateway.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            var output = useCase.execute(CreateClientUseCaseInputDto.builder()
                    .name("")
                    .email("j@j.com")
                    .build());
        });
    }

    @Test
    public void shouldNotCreateClientWhenEmailIsMissing() {
        assertThrows(IllegalArgumentException.class, () -> {
            var output = useCase.execute(CreateClientUseCaseInputDto.builder()
                    .name("John")
                    .email("")
                    .build());
        });
    }

}
