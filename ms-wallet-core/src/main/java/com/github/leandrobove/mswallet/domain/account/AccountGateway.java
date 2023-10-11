package com.github.leandrobove.mswallet.domain.account;

import java.util.Optional;

public interface AccountGateway {

    Optional<Account> findById(AccountId accountId);

    void save(Account account);

    void updateBalance(Account account);
}
