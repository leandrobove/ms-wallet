package com.github.leandrobove.msbalance.event.listener;

import com.github.leandrobove.msbalance.event.AccountCreatedEvent;
import com.github.leandrobove.msbalance.model.Account;
import com.github.leandrobove.msbalance.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@Component
public class AccountCreatedEventListener implements Consumer<AccountCreatedEvent> {
    private final AccountService accountService;

    public AccountCreatedEventListener(final AccountService accountService) {
        this.accountService = Objects.requireNonNull(accountService);
    }


    @Override
    public void accept(AccountCreatedEvent accountCreatedEvent) {
        log.info("Consuming AccountCreatedEvent: {}", accountCreatedEvent);

        var account = new Account(accountCreatedEvent.getAccountId(), accountCreatedEvent.getBalance());

        accountService.save(account);
    }
}
