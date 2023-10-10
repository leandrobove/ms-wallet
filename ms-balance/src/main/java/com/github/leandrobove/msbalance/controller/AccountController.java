package com.github.leandrobove.msbalance.controller;

import com.github.leandrobove.msbalance.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    private final AccountService accountService;

    public AccountController(final AccountService accountService) {
        this.accountService = Objects.requireNonNull(accountService);
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<?> getBalanceByAccountId(@PathVariable String accountId) {
        try {
            var account = accountService.findById(accountId);

            return ResponseEntity.ok(new BalanceResponse(account.getId(), account.getBalance()));
        } catch (RuntimeException ex) {
            HttpStatus notFound = HttpStatus.NOT_FOUND;

            return new ResponseEntity<>(ProblemDetail.forStatusAndDetail(notFound, String.
                    format("account id %s not found", accountId)), notFound);
        }
    }
}
