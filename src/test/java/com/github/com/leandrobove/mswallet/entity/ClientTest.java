package com.github.com.leandrobove.mswallet.entity;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientTest {

    @Test
    public void shouldCreateNewClient() {
        Client client = Client.create("John", "j@j.com");

        assertThat(client.getId()).isNotNull();
        assertThat(client.getName()).isEqualTo("John");
        assertThat(client.getEmail()).isEqualTo("j@j.com");
        assertThat(client.getCreatedAt()).isNotNull();
        assertThat(client.getUpdatedAt()).isNotNull();
    }

    @Test
    public void shouldThrowExceptionWhenNameIsBlank() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create("", "j@j.com");
        });
        assertThat(ex.getMessage()).isEqualTo("name is required");
    }

    @Test
    public void shouldThrowExceptionWhenEmailIsBlank() {
        val ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client client = Client.create("John", "");
        });
        assertThat(ex.getMessage()).isEqualTo("email is required");
    }

    @Test
    public void shouldChangeName() {
        Client client = Client.create("John", "j@j.com");
        assertThat(client.getName()).isEqualTo("John");
        client.changeName("Maria");
        assertThat(client.getName()).isEqualTo("Maria");
    }

    @Test
    public void shouldChangeEmail() {
        Client client = Client.create("John", "j@j.com");
        assertThat(client.getEmail()).isEqualTo("j@j.com");
        client.changeEmail("m@m.com");
        assertThat(client.getEmail()).isEqualTo("m@m.com");
    }
}
