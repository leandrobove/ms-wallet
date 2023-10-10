package com.github.leandrobove.mswallet.domain.entity;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientTest {

    @Test
    public void shouldCreateNewClient() {
        Client client = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");

        assertThat(client.getId()).isNotNull();
        assertThat(client.getName().firstName()).isEqualTo("John");
        assertThat(client.getName().lastName()).isEqualTo("Brad");
        assertThat(client.getEmail().value()).isEqualTo("john@gmail.com");
        assertThat(client.getCpf().format()).isEqualTo("297.263.110-20");
        assertThat(client.getAccounts()).isNotNull();
        assertThat(client.getCreatedAt()).isNotNull();
        assertThat(client.getUpdatedAt()).isNotNull();
    }

    @Test
    public void shouldThrowExceptionWhenFirstNameIsBlank() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create("", "Brad", "john@gmail.com", "297.263.110-20");
        });
        assertThat(ex.getMessage()).isEqualTo("firstName is required");
    }

    @Test
    public void shouldThrowExceptionWhenFirstNameIsNull() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create(null, "Brad", "john@gmail.com", "297.263.110-20");
        });
        assertThat(ex.getMessage()).isEqualTo("firstName is required");
    }

    @Test
    public void shouldThrowExceptionWhenLastNameIsBlank() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create("John", "", "john@gmail.com", "297.263.110-20");
        });
        assertThat(ex.getMessage()).isEqualTo("lastName is required");
    }

    @Test
    public void shouldThrowExceptionWhenLastNameIsNull() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create("John", null, "john@gmail.com", "297.263.110-20");
        });
        assertThat(ex.getMessage()).isEqualTo("lastName is required");
    }

    @Test
    public void shouldThrowExceptionWhenEmailIsNull() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create("John", "Brad", null, "297.263.110-20");
        });
        assertThat(ex.getMessage()).isEqualTo("email address is required");
    }

    @Test
    public void shouldThrowExceptionWhenEmailIsBlank() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create("John", "Brad", "", "297.263.110-20");
        });
        assertThat(ex.getMessage()).isEqualTo("email address is required");
    }

    @Test
    public void shouldThrowExceptionWhenEmailIsInvalid() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create("John", "Brad", "j@j.com", "297.263.110-20");
        });
        assertThat(ex.getMessage()).isEqualTo("a valid email address is required");
    }

    @Test
    public void shouldThrowExceptionWhenCpfIsNull() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create("John", "Brad", "john@gmail.com", null);
        });
        assertThat(ex.getMessage()).isEqualTo("cpf is required");
    }

    @Test
    public void shouldThrowExceptionWhenCpfIsBlank() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create("John", "Brad", "john@gmail.com", "   ");
        });
        assertThat(ex.getMessage()).isEqualTo("cpf is required");
    }

    @Test
    public void shouldThrowExceptionWhenCpfIsInvalid() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create("John", "Brad", "john@gmail.com", "111.111.111-11");
        });
        assertThat(ex.getMessage()).isEqualTo("a valid CPF is required");
    }

    @Test
    public void shouldChangeName() {
        Client client = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        assertThat(client.getName().firstName()).isEqualTo("John");
        assertThat(client.getName().lastName()).isEqualTo("Brad");
        client.changeName(FullName.from("Johnny", "Brady"));
        assertThat(client.getName().firstName()).isEqualTo("Johnny");
        assertThat(client.getName().lastName()).isEqualTo("Brady");
    }

    @Test
    public void shouldChangeEmail() {
        Client client = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        assertThat(client.getEmail().value()).isEqualTo("john@gmail.com");
        client.changeEmail(Email.from("john2@gmail.com"));
        assertThat(client.getEmail().value()).isEqualTo("john2@gmail.com");
    }

    @Test
    public void shouldAddAccount() {
        Client client = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        Account account1 = Account.create(client);
        Account account2 = Account.create(client);

        client.addAccount(account1);
        client.addAccount(account2);

        assertThat(client.getAccounts().size()).isEqualTo(2);
        assertThat(client.getAccounts().get(0)).isEqualTo(account1);
        assertThat(client.getAccounts().get(1)).isEqualTo(account2);
    }

    @Test
    public void shouldNotAddAccountWhenAccountIsNull() {
        Client client = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            client.addAccount(null);
        });
        assertThat(ex.getMessage()).isEqualTo("account is required");
    }

    @Test
    public void shouldNotAddAccountThatDoesNotBelongClient() {
        Client client = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        Client client2 = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        Account account = Account.create(client2);

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            client.addAccount(account);
        });
        assertThat(ex.getMessage()).isEqualTo("account does not belong to client");
    }
}
