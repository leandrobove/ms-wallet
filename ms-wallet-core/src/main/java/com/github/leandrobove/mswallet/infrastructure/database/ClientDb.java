package com.github.leandrobove.mswallet.infrastructure.database;

import com.github.leandrobove.mswallet.application.gateway.ClientGateway;
import com.github.leandrobove.mswallet.domain.entity.CPF;
import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.entity.ClientId;
import com.github.leandrobove.mswallet.domain.entity.Email;
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
    public Optional<Client> findById(ClientId clientId) {
        String sql = """
                SELECT id, name, email, cpf, created_at, updated_at FROM client WHERE id = ?;
                """;
        return jdbcTemplate.query(sql, new ClientRowMapper(), clientId.value())
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Client> findByEmail(Email email) {
        String sql = """
                SELECT id, name, email, cpf, created_at, updated_at FROM client WHERE email = ?;
                """;
        return jdbcTemplate.query(sql, new ClientRowMapper(), email.value())
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Client> findByCpf(CPF cpf) {
        String sql = """
                SELECT id, name, email, cpf, created_at, updated_at FROM client WHERE cpf = ?;
                """;
        return jdbcTemplate.query(sql, new ClientRowMapper(), cpf.value())
                .stream()
                .findFirst();
    }

    @Override
    public void save(Client client) {
        String sql = """
                INSERT INTO client(id, name, email, cpf, created_at, updated_at) VALUES(?,?,?,?,?,?);
                """;
        jdbcTemplate.update(sql, client.getId().value(), client.getName().fullName(),
                client.getEmail().value(), client.getCpf().value(), client.getCreatedAt(), client.getUpdatedAt());
    }
}
