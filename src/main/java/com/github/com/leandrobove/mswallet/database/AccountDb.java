package com.github.com.leandrobove.mswallet.database;

import com.github.com.leandrobove.mswallet.entity.Account;
import com.github.com.leandrobove.mswallet.gateway.AccountGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountDb implements AccountGateway {
    private final JdbcTemplate jdbcTemplate;

    private final Logger log = LoggerFactory.getLogger(AccountDb.class);

    public AccountDb(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Account> find(String id) {
        var sql = "SELECT a.id, " +
                "a.balance, " +
                "a.created_at, " +
                "a.updated_at, " +
                "c.id, " +
                "c.name, " +
                "c.email, " +
                "c.created_at, " +
                "c.updated_at " +
                "FROM account a INNER JOIN client c " +
                "ON c.id = a.client_id " +
                "WHERE a.id = ?";
        return jdbcTemplate.query(sql, new AccountRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public void save(Account account) {
        var sql = """
                INSERT INTO account(id, client_id, balance, created_at, updated_at) VALUES(?,?,?,?,?);
                """;
        jdbcTemplate.update(sql, account.getId(), account.getClient().getId(), account.getBalance(),
                account.getCreatedAt(), account.getUpdatedAt());
    }
}
