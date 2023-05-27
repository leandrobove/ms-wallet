package com.github.com.leandrobove.mswallet.gateway;

import com.github.com.leandrobove.mswallet.entity.Client;

import java.util.Optional;

public interface ClientGateway {

    Optional<Client> find(String id);

    Optional<Client> findByEmail(String email);

    void save(Client client);
}
