package com.github.leandrobove.msbalance.event.listener;

import com.github.leandrobove.msbalance.event.BalanceUpdatedEvent;
import com.github.leandrobove.msbalance.model.Account;
import com.github.leandrobove.msbalance.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Consumer;

@Component
public class BalanceUpdatedEventListener implements Consumer<BalanceUpdatedEvent> {

    private static final Logger log = LoggerFactory.getLogger(BalanceUpdatedEventListener.class);

    private final AccountRepository accountRepository;

    public BalanceUpdatedEventListener(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void accept(BalanceUpdatedEvent balanceUpdatedEvent) {
        log.info("Consuming BalanceUpdatedEvent: {}", balanceUpdatedEvent);

        Account accountFrom = new Account(balanceUpdatedEvent.getPayload().getAccountIdFrom(),
                balanceUpdatedEvent.getPayload().getBalanceAccountFrom());
        Account accountTo = new Account(balanceUpdatedEvent.getPayload().getAccountIdTo(),
                balanceUpdatedEvent.getPayload().getBalanceAccountTo());

        //persist account into database
        accountRepository.saveAll(Arrays.asList(accountFrom, accountTo));
    }

}

