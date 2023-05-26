package com.github.com.leandrobove.mswallet.database;

import com.github.com.leandrobove.mswallet.database.rowmapper.ClientRowMapper;
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
                SELECT id, name, email, created_at, updated_at FROM client WHERE id = ?
                """;
        try {
            Client client = jdbcTemplate.queryForObject(sql, new ClientRowMapper(), id);
            return Optional.of(client);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Client client) {
        String sql = """
                INSERT INTO client(id, name, email, created_at, updated_at) VALUES(?,?,?,?,?);
                """;
        jdbcTemplate.update(sql, client.getId(), client.getName(),
                client.getEmail(), client.getCreatedAt(), client.getUpdatedAt());
    }
}
