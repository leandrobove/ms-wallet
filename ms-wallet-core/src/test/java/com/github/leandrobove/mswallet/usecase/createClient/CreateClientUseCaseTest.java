package com.github.leandrobove.mswallet.usecase.createClient;

import com.github.leandrobove.mswallet.application.gateway.ClientGateway;
import com.github.leandrobove.mswallet.application.usecase.createClient.CreateClientUseCase;
import com.github.leandrobove.mswallet.application.usecase.createClient.CreateClientUseCaseInput;
import com.github.leandrobove.mswallet.application.usecase.createClient.CreateClientUseCaseOutput;
import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.entity.Email;
import com.github.leandrobove.mswallet.domain.exception.EmailAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
        var input = CreateClientUseCaseInput.builder()
                .firstName("John")
                .lastName("Brad")
                .email("john@gmail.com")
                .build();

        CreateClientUseCaseOutput output = useCase.execute(input);

        verify(clientGateway, times(1)).save(any(Client.class));

        assertThat(output.getId()).isNotNull();
        assertThat(output.getName()).isEqualTo("John Brad");
        assertThat(output.getEmail()).isEqualTo("john@gmail.com");
        assertThat(output.getCreatedAt()).isNotNull();
        assertThat(output.getUpdatedAt()).isNotNull();
    }

    @Test
    public void shouldNotCreateClientWhenEmailAlreadyExists() {
        Email email = Email.from("john@gmail.com");
        when(clientGateway.findByEmail(email)).thenReturn(Optional.of(Client.create("John", "Brad", email.value())));

        var input = CreateClientUseCaseInput.builder()
                .firstName("John")
                .lastName("Brad")
                .email(email.value())
                .build();

        assertThrows(EmailAlreadyExistsException.class, () -> {
            var output = useCase.execute(input);
        });
    }

    @Test
    public void shouldNotCreateClientWhenFirstNameIsMissing() {
        var input = CreateClientUseCaseInput.builder()
                .firstName("")
                .lastName("Brad")
                .email("john@gmail.com")
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            var output = useCase.execute(input);
        });
    }

    @Test
    public void shouldNotCreateClientWhenLastNameIsMissing() {
        var input = CreateClientUseCaseInput.builder()
                .firstName("John")
                .lastName("")
                .email("john@gmail.com")
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            var output = useCase.execute(input);
        });
    }

    @Test
    public void shouldNotCreateClientWhenEmailIsMissing() {
        var input = CreateClientUseCaseInput.builder()
                .firstName("John")
                .lastName("Brad")
                .email("")
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            var output = useCase.execute(input);
        });
    }

}
