package com.github.com.leandrobove.mswallet.database;

import com.github.com.leandrobove.mswallet.entity.Account;
import com.github.com.leandrobove.mswallet.entity.Client;
import com.github.com.leandrobove.mswallet.entity.Transaction;
import com.github.com.leandrobove.mswallet.gateway.AccountGateway;
import com.github.com.leandrobove.mswallet.gateway.ClientGateway;
import com.github.com.leandrobove.mswallet.gateway.TransactionGateway;
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
        Client client1 = Client.create("John", "j@j.com");
        Client client2 = Client.create("Mariah", "m@j.com");
        clientGateway.save(client1);
        clientGateway.save(client2);
        assertThat(clientGateway.find(client1.getId().toString())).isPresent();
        assertThat(clientGateway.find(client2.getId().toString())).isPresent();

        Account accountFrom = Account.create(client1);
        Account accountTo = Account.create(client2);
        accountFrom.credit(new BigDecimal(1000.00));
        accountGateway.save(accountFrom);
        accountGateway.save(accountTo);
        assertThat(accountGateway.find(accountFrom.getId().toString())).isPresent();
        assertThat(accountGateway.find(accountTo.getId().toString())).isPresent();

        Transaction transaction = new Transaction(accountFrom, accountTo, new BigDecimal(50.00));
        transactionGateway.create(transaction);

        var sql = """
                SELECT count(*) FROM transaction WHERE id = ?
                """;
        Integer exists = this.jdbcTemplate.queryForObject(sql, Integer.class, transaction.getId());
        assertThat(exists).isEqualTo(1);
    }
}
