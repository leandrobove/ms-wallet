package com.github.leandrobove.msbalance.service;

import com.github.leandrobove.msbalance.model.Account;
import com.github.leandrobove.msbalance.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(final AccountRepository accountRepository) {
        this.accountRepository = Objects.requireNonNull(accountRepository);
    }

    @Transactional
    public void save(final Account account) {
        accountRepository.save(account);
    }

    @Transactional
    public void save(final List<Account> accounts) {
        accountRepository.saveAll(accounts);
    }

    public Account findById(String accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException(String.
                format("account id %s not found", accountId)));
    }
}
