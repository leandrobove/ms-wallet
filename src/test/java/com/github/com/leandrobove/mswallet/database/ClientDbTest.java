package com.github.com.leandrobove.mswallet.database;

import com.github.com.leandrobove.mswallet.entity.Client;
import com.github.com.leandrobove.mswallet.gateway.ClientGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ClientDbTest {

    @Autowired
    private ClientGateway clientGateway;

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
