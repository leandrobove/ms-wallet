package com.github.leandrobove.mswallet.application.gateway;

import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.entity.ClientId;

import java.util.Optional;

public interface ClientGateway {

    Optional<Client> findById(ClientId clientId);

    Optional<Client> findByEmail(String email);

    void save(Client client);
}
