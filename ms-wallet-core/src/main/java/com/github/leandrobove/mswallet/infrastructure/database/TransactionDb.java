package com.github.leandrobove.mswallet.infrastructure.database;

import com.github.leandrobove.mswallet.domain.entity.Transaction;
import com.github.leandrobove.mswallet.application.gateway.TransactionGateway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDb implements TransactionGateway {

    private final JdbcTemplate jdbcTemplate;

    public TransactionDb(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Transaction transaction) {
        var sql = """
                INSERT INTO transaction(id, amount, created_at, account_from_id, account_to_id) VALUES(?,?,?,?,?);
                """;
        jdbcTemplate.update(sql,
                transaction.getId().value(),
                transaction.getAmount().value(),
                transaction.getCreatedAt(),
                transaction.getAccountFrom().getId().value(),
                transaction.getAccountTo().getId().value()
        );
    }
}
