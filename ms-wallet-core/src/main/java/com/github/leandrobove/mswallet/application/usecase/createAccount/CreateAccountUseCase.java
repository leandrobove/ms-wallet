package com.github.leandrobove.mswallet.application.usecase.createAccount;

import com.github.leandrobove.mswallet.application.gateway.AccountGateway;
import com.github.leandrobove.mswallet.application.gateway.ClientGateway;
import com.github.leandrobove.mswallet.domain.entity.Account;
import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

@Service
public class CreateAccountUseCase {
    private final AccountGateway accountGateway;
    private final ClientGateway clientGateway;

    public CreateAccountUseCase(
            final AccountGateway accountGateway,
            final ClientGateway clientGateway
    ) {
        this.accountGateway = accountGateway;
        this.clientGateway = clientGateway;
    }

    @Transactional
    public CreateAccountUseCaseOutput execute(CreateAccountUseCaseInput input) {
        this.validateInput(input);

        Client client = this.findOrFail(input.getClientId());

        Account account = Account.create(client);

        accountGateway.save(account);

        return CreateAccountUseCaseOutput.builder()
                .id(account.getId().toString())
                .build();
    }

    private Client findOrFail(String clientId) {
        return clientGateway.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("client id %s not found", clientId)));
    }

    private void validateInput(CreateAccountUseCaseInput input) {
        if (isNull(input.getClientId()) || input.getClientId() == "") {
            throw new IllegalArgumentException("client id is required");
        }
    }
}