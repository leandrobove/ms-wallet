package com.github.leandrobove.msbalance.event.listener;

import com.github.leandrobove.msbalance.event.BalanceUpdatedEvent;
import com.github.leandrobove.msbalance.model.Account;
import com.github.leandrobove.msbalance.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@Component
public class BalanceUpdatedEventListener implements Consumer<BalanceUpdatedEvent> {
    private final AccountService accountService;

    public BalanceUpdatedEventListener(final AccountService accountService) {
        this.accountService = Objects.requireNonNull(accountService);
    }

    @Override
    public void accept(BalanceUpdatedEvent balanceUpdatedEvent) {
        log.info("Consuming BalanceUpdatedEvent: {}", balanceUpdatedEvent);

        Account accountFrom = new Account(balanceUpdatedEvent.getAccountIdFrom(),
                balanceUpdatedEvent.getBalanceAccountFrom());
        Account accountTo = new Account(balanceUpdatedEvent.getAccountIdTo(),
                balanceUpdatedEvent.getBalanceAccountTo());

        //persist account into database
        accountService.save(Arrays.asList(accountFrom, accountTo));
    }

}

