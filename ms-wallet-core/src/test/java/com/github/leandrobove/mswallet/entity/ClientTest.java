package com.github.leandrobove.mswallet.entity;

import com.github.leandrobove.mswallet.domain.entity.Account;
import com.github.leandrobove.mswallet.domain.entity.Client;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientTest {

    @Test
    public void shouldCreateNewClient() {
        Client client = Client.create("John", "john@gmail.com");

        assertThat(client.getId()).isNotNull();
        assertThat(client.getName()).isEqualTo("John");
        assertThat(client.getEmail().value()).isEqualTo("john@gmail.com");
        assertThat(client.getAccounts()).isNotNull();
        assertThat(client.getCreatedAt()).isNotNull();
        assertThat(client.getUpdatedAt()).isNotNull();
    }

    @Test
    public void shouldThrowExceptionWhenNameIsBlank() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create("", "j@gmail.com");
        });
        assertThat(ex.getMessage()).isEqualTo("name is required");
    }

    @Test
    public void shouldThrowExceptionWhenNameIsNull() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create(null, "j@gmail.com");
        });
        assertThat(ex.getMessage()).isEqualTo("name is required");
    }

    @Test
    public void shouldThrowExceptionWhenEmailIsNull() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create("John", null);
        });
        assertThat(ex.getMessage()).isEqualTo("email address is required");
    }

    @Test
    public void shouldThrowExceptionWhenEmailIsBlank() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create("John", "");
        });
        assertThat(ex.getMessage()).isEqualTo("email address is required");
    }

    @Test
    public void shouldChangeName() {
        Client client = Client.create("John", "john@gmail.com");
        assertThat(client.getName()).isEqualTo("John");
        client.changeName("Maria");
        assertThat(client.getName()).isEqualTo("Maria");
    }

    @Test
    public void shouldChangeEmail() {
        Client client = Client.create("John", "john@gmail.com");
        assertThat(client.getEmail().value()).isEqualTo("john@gmail.com");
        client.changeEmail("john2@gmail.com");
        assertThat(client.getEmail().value()).isEqualTo("john2@gmail.com");
    }

    @Test
    public void shouldAddAccount() {
        Client client = Client.create("John", "john@gmail.com");
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
        Client client = Client.create("John", "john@gmail.com");

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            client.addAccount(null);
        });
        assertThat(ex.getMessage()).isEqualTo("account is required");
    }

    @Test
    public void shouldNotAddAccountThatDoesNotBelongClient() {
        Client client = Client.create("John", "john@gmail.com");
        Client client2 = Client.create("John", "john@gmail.com");
        Account account = Account.create(client2);

        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            client.addAccount(account);
        });
        assertThat(ex.getMessage()).isEqualTo("account does not belong to client");
    }
}
