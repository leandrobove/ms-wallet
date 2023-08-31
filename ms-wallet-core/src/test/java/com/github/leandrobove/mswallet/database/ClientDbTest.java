package com.github.leandrobove.mswallet.database;

import com.github.leandrobove.mswallet.application.gateway.ClientGateway;
import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.entity.ClientId;
import com.github.leandrobove.mswallet.infrastructure.database.ClientDb;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
public class ClientDbTest {
    private JdbcTemplate jdbcTemplate;
    private ClientGateway clientGateway;

    @Autowired
    public ClientDbTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.clientGateway = new ClientDb(jdbcTemplate);
    }

    @Test
    public void shouldSaveClient() {
        Client client = Client.create("John", "john@gmail.com");
        clientGateway.save(client);

        Client clientFound = clientGateway.findById(client.getId()).get();

        assertThat(client.getId()).isEqualTo(clientFound.getId());
        assertThat(client.getName()).isEqualTo(clientFound.getName());
        assertThat(client.getEmail()).isEqualTo(clientFound.getEmail());
        assertThat(client.getCreatedAt()).isNotNull();
        assertThat(client.getUpdatedAt()).isNotNull();
    }

    @Test
    public void shouldNotFindClient() {
        Optional<Client> client = clientGateway.findById(ClientId.unique());

        assertThat(client).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldFindClientByEmail() {
        Client client = Client.create("John", "john@gmail.com");
        clientGateway.save(client);

        Optional<Client> clientOptional = clientGateway.findByEmail(client.getEmail().value());

        assertThat(clientOptional).isPresent();
        assertThat(clientOptional.get().getEmail()).isEqualTo(client.getEmail());
    }

    @Test
    public void shouldNotFindClientByEmail() {
        Optional<Client> clientOptional = clientGateway.findByEmail("nonexistingemail@gmail.com");
        assertThat(clientOptional).isEmpty();
    }
}
