package com.github.leandrobove.mswallet.application.gateway;

import com.github.leandrobove.mswallet.domain.entity.Account;

import java.util.Optional;

public interface AccountGateway {

    Optional<Account> findById(String accountId);

    void save(Account account);

    void updateBalance(Account account);
}
