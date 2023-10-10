package com.github.leandrobove.msbalance.controller;

import com.github.leandrobove.msbalance.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getBalanceByAccountId(@PathVariable String accountId) {
        var optionalAccount = accountRepository.findById(accountId);

        if (optionalAccount.isEmpty()) {
            HttpStatus notFound = HttpStatus.NOT_FOUND;

            return new ResponseEntity<>(ProblemDetail.forStatusAndDetail(notFound, String.
                    format("account id %s not found", accountId)), notFound);
        }

        var account = optionalAccount.get();
        return ResponseEntity.ok(new BalanceResponse(account.getId(), account.getBalance()));
    }
}
