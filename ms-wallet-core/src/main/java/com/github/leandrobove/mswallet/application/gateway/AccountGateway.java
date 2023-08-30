package com.github.leandrobove.mswallet.application.gateway;

import com.github.leandrobove.mswallet.domain.entity.Account;
import com.github.leandrobove.mswallet.domain.entity.AccountId;

import java.util.Optional;

public interface AccountGateway {

    Optional<Account> findById(AccountId accountId);

    void save(Account account);

    void updateBalance(Account account);
}
