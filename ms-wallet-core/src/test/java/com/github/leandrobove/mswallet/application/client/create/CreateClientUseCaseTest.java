package com.github.leandrobove.mswallet.application.client.create;

import com.github.leandrobove.mswallet.application.client.create.CreateClientUseCase;
import com.github.leandrobove.mswallet.application.client.create.CreateClientUseCaseInput;
import com.github.leandrobove.mswallet.application.client.create.CreateClientUseCaseOutput;
import com.github.leandrobove.mswallet.domain.client.ClientGateway;
import com.github.leandrobove.mswallet.domain.client.CPF;
import com.github.leandrobove.mswallet.domain.client.Client;
import com.github.leandrobove.mswallet.domain.client.Email;
import com.github.leandrobove.mswallet.domain.client.CpfAlreadyExistsException;
import com.github.leandrobove.mswallet.domain.client.EmailAlreadyExistsException;
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
                .cpf("297.263.110-20")
                .build();

        CreateClientUseCaseOutput output = useCase.execute(input);

        verify(clientGateway, times(1)).save(any(Client.class));

        assertThat(output.getId()).isNotNull();
        assertThat(output.getName()).isEqualTo("John Brad");
        assertThat(output.getEmail()).isEqualTo("john@gmail.com");
        assertThat(output.getCpf()).isEqualTo("29726311020");
        assertThat(output.getCreatedAt()).isNotNull();
        assertThat(output.getUpdatedAt()).isNotNull();
    }

    @Test
    public void shouldNotCreateClientWhenEmailAlreadyExists() {
        Email email = Email.from("john@gmail.com");
        when(clientGateway.findByEmail(email)).thenReturn(Optional.of(Client.create("John", "Brad", email.value(), "297.263.110-20")));

        var input = CreateClientUseCaseInput.builder()
                .firstName("John")
                .lastName("Brad")
                .email(email.value())
                .cpf("297.263.110-20")
                .build();

        assertThrows(EmailAlreadyExistsException.class, () -> {
            var output = useCase.execute(input);
        });
    }

    @Test
    public void shouldNotCreateClientWhenCpfAlreadyExists() {
        CPF cpf = CPF.from("297.263.110-20");
        when(clientGateway.findByCpf(cpf)).thenReturn(Optional.of(Client.create("John", "Brad", "john@gmail.com", "297.263.110-20")));

        var input = CreateClientUseCaseInput.builder()
                .firstName("John")
                .lastName("Brad")
                .email("john@gmail.com")
                .cpf("297.263.110-20")
                .build();

        assertThrows(CpfAlreadyExistsException.class, () -> {
            var output = useCase.execute(input);
        });
    }

    @Test
    public void shouldNotCreateClientWhenFirstNameIsMissing() {
        var input = CreateClientUseCaseInput.builder()
                .firstName("")
                .lastName("Brad")
                .email("john@gmail.com")
                .cpf("297.263.110-20")
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
                .cpf("297.263.110-20")
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
                .cpf("297.263.110-20")
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            var output = useCase.execute(input);
        });
    }

    @Test
    public void shouldNotCreateClientWhenCpfIsMissing() {
        var input = CreateClientUseCaseInput.builder()
                .firstName("John")
                .lastName("Brad")
                .email("john@gmail.com")
                .cpf("")
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            var output = useCase.execute(input);
        });
    }

}
