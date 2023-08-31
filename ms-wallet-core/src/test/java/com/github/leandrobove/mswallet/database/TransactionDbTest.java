package com.github.leandrobove.mswallet.database;

import com.github.leandrobove.mswallet.application.gateway.AccountGateway;
import com.github.leandrobove.mswallet.application.gateway.ClientGateway;
import com.github.leandrobove.mswallet.application.gateway.TransactionGateway;
import com.github.leandrobove.mswallet.domain.entity.Account;
import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.entity.Transaction;
import com.github.leandrobove.mswallet.infrastructure.database.AccountDb;
import com.github.leandrobove.mswallet.infrastructure.database.ClientDb;
import com.github.leandrobove.mswallet.infrastructure.database.TransactionDb;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
public class TransactionDbTest {

    private AccountGateway accountGateway;
    private ClientGateway clientGateway;
    private TransactionGateway transactionGateway;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionDbTest(JdbcTemplate jdbcTemplate) {
        this.accountGateway = new AccountDb(jdbcTemplate);
        this.clientGateway = new ClientDb(jdbcTemplate);
        this.transactionGateway = new TransactionDb(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    public void shouldSaveAccount() {
        Client client1 = Client.create("John", "john@gmail.com");
        Client client2 = Client.create("Mariah", "maria@hotmail.com");
        clientGateway.save(client1);
        clientGateway.save(client2);
        assertThat(clientGateway.findById(client1.getId())).isPresent();
        assertThat(clientGateway.findById(client2.getId())).isPresent();

        Account accountFrom = Account.create(client1);
        Account accountTo = Account.create(client2);
        accountFrom.credit(new BigDecimal(1000.00));
        accountGateway.save(accountFrom);
        accountGateway.save(accountTo);
        assertThat(accountGateway.findById(accountFrom.getId())).isPresent();
        assertThat(accountGateway.findById(accountTo.getId())).isPresent();

        Transaction transaction = Transaction.transfer(accountFrom, accountTo, new BigDecimal(50.00));
        transactionGateway.create(transaction);

        var sql = """
                SELECT count(*) FROM transaction WHERE id = ?
                """;
        Integer exists = this.jdbcTemplate.queryForObject(sql, Integer.class, transaction.getId().value());
        assertThat(exists).isEqualTo(1);
    }
}
