package com.github.leandrobove.mswallet.infrastructure.database;

import com.github.leandrobove.mswallet.application.gateway.AccountGateway;
import com.github.leandrobove.mswallet.application.gateway.ClientGateway;
import com.github.leandrobove.mswallet.domain.entity.Account;
import com.github.leandrobove.mswallet.domain.entity.AccountId;
import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.infrastructure.database.AccountDb;
import com.github.leandrobove.mswallet.infrastructure.database.ClientDb;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
public class AccountDbTest {
    private JdbcTemplate jdbcTemplate;
    private AccountGateway accountGateway;
    private ClientGateway clientGateway;

    @Autowired
    public AccountDbTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountGateway = new AccountDb(jdbcTemplate);
        this.clientGateway = new ClientDb(jdbcTemplate);
    }

    @Test
    public void shouldSaveAccount() {
        Client client = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        clientGateway.save(client);
        assertThat(clientGateway.findById(client.getId())).isPresent();

        Account account = Account.create(client);
        accountGateway.save(account);

        Optional<Account> accountOptional = accountGateway.findById(account.getId());
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
        Optional<Account> account = accountGateway.findById(AccountId.unique());
        assertThat(account).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldUpdateAccountBalance() {
        Client client = Client.create("John", "Brad", "john@gmail.com", "297.263.110-20");
        clientGateway.save(client);
        assertThat(clientGateway.findById(client.getId())).isPresent();

        Account account = Account.create(client);
        accountGateway.save(account);
        assertThat(accountGateway.findById(account.getId())).isPresent();

        account.credit(new BigDecimal(1000.00));
        accountGateway.updateBalance(account);
        Account accountUpdated = accountGateway.findById(account.getId()).get();
        assertThat(accountUpdated.getBalance().compareTo(new BigDecimal(1000.00))).isEqualTo(0);
    }
}
