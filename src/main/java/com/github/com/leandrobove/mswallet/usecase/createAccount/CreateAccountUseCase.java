package com.github.com.leandrobove.mswallet.usecase.createAccount;

import com.github.com.leandrobove.mswallet.entity.Account;
import com.github.com.leandrobove.mswallet.entity.Client;
import com.github.com.leandrobove.mswallet.exception.EntityNotFoundException;
import com.github.com.leandrobove.mswallet.gateway.AccountGateway;
import com.github.com.leandrobove.mswallet.gateway.ClientGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

@Service
public class CreateAccountUseCase {
    private final AccountGateway accountGateway;
    private final ClientGateway clientGateway;

    public CreateAccountUseCase(AccountGateway accountGateway, ClientGateway clientGateway) {
        this.accountGateway = accountGateway;
        this.clientGateway = clientGateway;
    }

    @Transactional
    public CreateAccountUseCaseOutputDto execute(CreateAccountUseCaseInputDto input) throws EntityNotFoundException {
        this.validateInput(input);

        Client client = clientGateway.find(input.getClientId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("client id %s not found", input.getClientId())));

        Account account = Account.create(client);

        accountGateway.save(account);

        return CreateAccountUseCaseOutputDto.builder()
                .id(account.getId().toString())
                .build();
    }

    private void validateInput(CreateAccountUseCaseInputDto input) {
        if (isNull(input.getClientId()) || input.getClientId() == "") {
            throw new IllegalArgumentException("client id is required");
        }
    }
}
