package com.github.leandrobove.mswallet.infrastructure.database;

import com.github.leandrobove.mswallet.application.gateway.AccountGateway;
import com.github.leandrobove.mswallet.domain.entity.Account;
import com.github.leandrobove.mswallet.domain.entity.AccountId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountDb implements AccountGateway {
    private final JdbcTemplate jdbcTemplate;

    public AccountDb(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Account> findById(AccountId accountId) {
        var sql = "SELECT a.id, " +
                "a.balance, " +
                "a.created_at, " +
                "a.updated_at, " +
                "c.id, " +
                "c.name, " +
                "c.email, " +
                "c.cpf, " +
                "c.created_at, " +
                "c.updated_at " +
                "FROM account a INNER JOIN client c " +
                "ON c.id = a.client_id " +
                "WHERE a.id = ? " +
                "FOR UPDATE;";
        return jdbcTemplate.query(sql, new AccountRowMapper(), accountId.value())
                .stream()
                .findFirst();
    }

    @Override
    public void save(Account account) {
        var sql = """
                INSERT INTO account(id, client_id, balance, created_at, updated_at) VALUES(?,?,?,?,?);
                """;
        jdbcTemplate.update(sql, account.getId().value(), account.getClient().getId().value(), account.getBalance().value(),
                account.getCreatedAt(), account.getUpdatedAt());
    }

    @Override
    public void updateBalance(Account account) {
        var sql = """
                UPDATE account SET balance = ? WHERE id = ?;
                """;

        jdbcTemplate.update(sql, account.getBalance().value(), account.getId().value());
    }
}
