package com.github.leandrobove.mswallet.application.gateway;

import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.entity.ClientId;
import com.github.leandrobove.mswallet.domain.entity.Email;

import java.util.Optional;

public interface ClientGateway {

    Optional<Client> findById(ClientId clientId);

    Optional<Client> findByEmail(Email email);

    void save(Client client);
}
