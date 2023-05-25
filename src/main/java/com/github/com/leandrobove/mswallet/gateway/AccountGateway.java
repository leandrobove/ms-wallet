package com.github.com.leandrobove.mswallet.gateway;

import com.github.com.leandrobove.mswallet.entity.Account;

import java.util.Optional;

public interface AccountGateway {

    Optional<Account> find(String id);

    void save(Account client);
}
