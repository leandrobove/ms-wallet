package com.github.leandrobove.mswallet.application.usecase.createAccount;

import com.github.leandrobove.mswallet.application.gateway.AccountGateway;
import com.github.leandrobove.mswallet.application.gateway.ClientGateway;
import com.github.leandrobove.mswallet.domain.entity.Account;
import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.entity.ClientId;
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
    public CreateAccountUseCaseOutput execute(final CreateAccountUseCaseInput input) {
        this.validateInput(input);

        Client client = this.findOrFail(input.getClientId());

        Account account = Account.create(client);

        accountGateway.save(account);

        return CreateAccountUseCaseOutput.from(account);
    }

    private Client findOrFail(final String clientId) {
        return clientGateway.findById(ClientId.from(clientId))
                .orElseThrow(() -> new EntityNotFoundException(String.format("client id %s not found", clientId)));
    }

    private void validateInput(final CreateAccountUseCaseInput input) {
        if (isNull(input.getClientId()) || input.getClientId() == "") {
            throw new IllegalArgumentException("client id is required");
        }
    }
}
