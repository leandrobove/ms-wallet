package com.github.com.leandrobove.mswallet.database;

import com.github.com.leandrobove.mswallet.entity.Client;
import com.github.com.leandrobove.mswallet.gateway.ClientGateway;
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
        Client client = Client.create("John", "j@j.com");
        clientGateway.save(client);

        Client clientFound = clientGateway.find(client.getId().toString()).get();

        assertThat(client.getId()).isEqualTo(clientFound.getId());
        assertThat(client.getName()).isEqualTo(clientFound.getName());
        assertThat(client.getEmail()).isEqualTo(clientFound.getEmail());
        assertThat(client.getCreatedAt()).isNotNull();
        assertThat(client.getUpdatedAt()).isNotNull();
    }

    @Test
    public void shouldNotFindClient() {
        Optional<Client> client = clientGateway.find(UUID.randomUUID().toString());

        assertThat(client).isEqualTo(Optional.empty());
    }
}
