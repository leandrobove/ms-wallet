package com.github.leandrobove.mswallet.gateway;

import com.github.leandrobove.mswallet.entity.Account;

import java.util.Optional;

public interface AccountGateway {

    Optional<Account> find(String id);

    void save(Account account);

    void updateBalance(Account account);
}
