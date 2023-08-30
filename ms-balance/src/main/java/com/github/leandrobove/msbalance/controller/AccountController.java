package com.github.leandrobove.msbalance.controller;

import com.github.leandrobove.msbalance.model.Account;
import com.github.leandrobove.msbalance.repository.AccountRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    private final AccountRepository accountRepository;

    public AccountController(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("/{accountId}/balance")
    public Account getBalanceByAccountId(@PathVariable String accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException(String.
                format("account id %s not found", accountId)));
    }
}
