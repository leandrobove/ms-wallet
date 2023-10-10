package com.github.leandrobove.msbalance.api.controller;

import com.github.leandrobove.msbalance.api.AccountAPI;
import com.github.leandrobove.msbalance.api.dto.BalanceResponse;
import com.github.leandrobove.msbalance.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class AccountController implements AccountAPI {
    private final AccountService accountService;

    public AccountController(final AccountService accountService) {
        this.accountService = Objects.requireNonNull(accountService);
    }

    @Override
    public ResponseEntity<?> getBalanceByAccountId(String accountId) {
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
