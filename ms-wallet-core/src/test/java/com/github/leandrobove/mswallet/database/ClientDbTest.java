package com.github.leandrobove.mswallet.database;

import com.github.leandrobove.mswallet.application.gateway.ClientGateway;
import com.github.leandrobove.mswallet.domain.entity.CPF;
import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.entity.ClientId;
import com.github.leandrobove.mswallet.domain.entity.Email;
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
        Client client = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        clientGateway.save(client);

        Client clientFound = clientGateway.findById(client.getId()).get();

        assertThat(client.getId()).isEqualTo(clientFound.getId());
        assertThat(client.getName()).isEqualTo(clientFound.getName());
        assertThat(client.getEmail()).isEqualTo(clientFound.getEmail());
        assertThat(client.getCpf()).isEqualTo(clientFound.getCpf());
        assertThat(client.getCreatedAt()).isNotNull();
        assertThat(client.getUpdatedAt()).isNotNull();
    }

    @Test
    public void shouldNotFindClientById() {
        Optional<Client> client = clientGateway.findById(ClientId.unique());

        assertThat(client).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldFindClientByEmail() {
        Client client = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        clientGateway.save(client);

        Optional<Client> clientOptional = clientGateway.findByEmail(client.getEmail());

        assertThat(clientOptional).isPresent();
        assertThat(clientOptional.get().getEmail()).isEqualTo(client.getEmail());
    }

    @Test
    public void shouldNotFindClientByEmail() {
        Email nonExistingEmail = Email.from("nonexistingemail@gmail.com");
        Optional<Client> clientOptional = clientGateway.findByEmail(nonExistingEmail);
        assertThat(clientOptional).isEmpty();
    }

    @Test
    public void shouldFindClientByCpf() {
        Client client = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        clientGateway.save(client);

        Optional<Client> clientOptional = clientGateway.findByCpf(client.getCpf());

        assertThat(clientOptional).isPresent();
        assertThat(clientOptional.get().getCpf()).isEqualTo(client.getCpf());
    }

    @Test
    public void shouldNotFindClientByCpf() {
        var nonExistingCpf = CPF.from("297.263.110-20");
        Optional<Client> clientOptional = clientGateway.findByCpf(nonExistingCpf);
        assertThat(clientOptional).isEmpty();
    }
}
