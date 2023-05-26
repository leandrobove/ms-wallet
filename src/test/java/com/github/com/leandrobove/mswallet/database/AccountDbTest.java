package com.github.com.leandrobove.mswallet.database;

import com.github.com.leandrobove.mswallet.entity.Account;
import com.github.com.leandrobove.mswallet.entity.Client;
import com.github.com.leandrobove.mswallet.gateway.AccountGateway;
import com.github.com.leandrobove.mswallet.gateway.ClientGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AccountDbTest {

    @Autowired
    private AccountGateway accountGateway;

    @Autowired
    private ClientGateway clientGateway;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        jdbcTemplate.update("delete from account");
        jdbcTemplate.update("delete from client");
    }

    @Test
    public void shouldSaveAccount() {
        Client client = Client.create("John", "j@j.com");
        clientGateway.save(client);
        assertThat(clientGateway.find(client.getId().toString())).isPresent();

        Account account = Account.create(client);
        accountGateway.save(account);

        Optional<Account> accountOptional = accountGateway.find(account.getId().toString());
        assertThat(accountOptional).isPresent();

        Account accountFound = accountOptional.get();
        assertThat(accountFound.getId()).isEqualTo(account.getId());
        assertThat(accountFound.getBalance().doubleValue()).isEqualTo(account.getBalance().doubleValue());
        assertThat(accountFound.getCreatedAt()).isNotNull();
        assertThat(accountFound.getUpdatedAt()).isNotNull();

        assertThat(accountFound.getClient().getId()).isEqualTo(account.getClient().getId());
        assertThat(accountFound.getClient().getName()).isEqualTo(account.getClient().getName());
        assertThat(accountFound.getClient().getEmail()).isEqualTo(account.getClient().getEmail());
        assertThat(accountFound.getClient().getCreatedAt()).isNotNull();
        assertThat(accountFound.getClient().getUpdatedAt()).isNotNull();
    }

    @Test
    public void shouldNotFindAccount() {
        Optional<Account> account = accountGateway.find(UUID.randomUUID().toString());
        assertThat(account).isEqualTo(Optional.empty());
    }
}
