package com.github.leandrobove.mswallet.domain.client;

import java.util.Optional;

public interface ClientGateway {

    Optional<Client> findById(ClientId clientId);

    Optional<Client> findByEmail(Email email);

    Optional<Client> findByCpf(CPF cpf);

    void save(Client client);
}
