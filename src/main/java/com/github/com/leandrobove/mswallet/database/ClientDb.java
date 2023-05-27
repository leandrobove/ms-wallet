package com.github.com.leandrobove.mswallet.database;

import com.github.com.leandrobove.mswallet.entity.Client;
import com.github.com.leandrobove.mswallet.gateway.ClientGateway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClientDb implements ClientGateway {

    private final JdbcTemplate jdbcTemplate;

    public ClientDb(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Client> find(String id) {
        String sql = """
                SELECT id, name, email, created_at, updated_at FROM client WHERE id = ?;
                """;
        return jdbcTemplate.query(sql, new ClientRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        String sql = """
                SELECT id, name, email, created_at, updated_at FROM client WHERE email = ?;
                """;
        return jdbcTemplate.query(sql, new ClientRowMapper(), email)
                .stream()
                .findFirst();
    }

    @Override
    public void save(Client client) {
        String sql = """
                INSERT INTO client(id, name, email, created_at, updated_at) VALUES(?,?,?,?,?);
                """;
        jdbcTemplate.update(sql, client.getId().toString(), client.getName(),
                client.getEmail(), client.getCreatedAt(), client.getUpdatedAt());
    }
}
