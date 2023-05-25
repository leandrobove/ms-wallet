package com.github.com.leandrobove.mswallet.gateway;

import com.github.com.leandrobove.mswallet.entity.Client;

public interface ClientGateway {

    Client get(String id);

    void save(Client client);
}
