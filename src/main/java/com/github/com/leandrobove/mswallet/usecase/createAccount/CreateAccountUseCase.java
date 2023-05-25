package com.github.com.leandrobove.mswallet.usecase.createAccount;

import com.github.com.leandrobove.mswallet.entity.Account;
import com.github.com.leandrobove.mswallet.entity.Client;
import com.github.com.leandrobove.mswallet.exception.EntityNotFoundException;
import com.github.com.leandrobove.mswallet.gateway.AccountGateway;
import com.github.com.leandrobove.mswallet.gateway.ClientGateway;


public class CreateAccountUseCase {
    private final AccountGateway accountGateway;
    private final ClientGateway clientGateway;

    public CreateAccountUseCase(AccountGateway accountGateway, ClientGateway clientGateway) {
        this.accountGateway = accountGateway;
        this.clientGateway = clientGateway;
    }

    public CreateAccountUseCaseOutputDto execute(CreateAccountUseCaseInputDto input) throws EntityNotFoundException {
        Client client = clientGateway.find(input.getClientId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("client id %s not found", input.getClientId())));

        Account account = Account.create(client);

        accountGateway.save(account);

        return CreateAccountUseCaseOutputDto.builder()
                .id(account.getId().toString())
                .build();
    }
}
