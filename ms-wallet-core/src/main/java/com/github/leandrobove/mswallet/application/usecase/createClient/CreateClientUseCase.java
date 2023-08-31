package com.github.leandrobove.mswallet.application.usecase.createClient;

import com.github.leandrobove.mswallet.application.gateway.ClientGateway;
import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.entity.Email;
import com.github.leandrobove.mswallet.domain.exception.EmailAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Objects.isNull;

@Service
public class CreateClientUseCase {
    private final ClientGateway clientGateway;

    public CreateClientUseCase(final ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    @Transactional
    public CreateClientUseCaseOutput execute(final CreateClientUseCaseInput input) {
        this.validateInput(input);

        var name = input.getName();
        var email = input.getEmail();

        this.verifyEmailAlreadyExists(email);

        Client client = Client.create(name, email);

        clientGateway.save(client);

        return CreateClientUseCaseOutput.from(client);
    }

    private void verifyEmailAlreadyExists(final String email) {
        //verify whether email already exists
        if (clientGateway.findByEmail(Email.from(email)).isPresent()) {
            throw new EmailAlreadyExistsException(String.format("email %s already exists", email));
        }
    }

    private void validateInput(final CreateClientUseCaseInput input) {
        if (isNull(input.getEmail()) || input.getEmail() == "") {
            throw new IllegalArgumentException("email is required");
        }
    }
}
