package com.github.leandrobove.mswallet.application.usecase.createClient;

import com.github.leandrobove.mswallet.application.gateway.ClientGateway;
import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.entity.Email;
import com.github.leandrobove.mswallet.domain.exception.EmailAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateClientUseCase {
    private final ClientGateway clientGateway;

    public CreateClientUseCase(final ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    @Transactional
    public CreateClientUseCaseOutput execute(final CreateClientUseCaseInput input) {

        var firstName = input.getFirstName();
        var lastName = input.getLastName();
        var email = input.getEmail();

        this.verifyEmailAlreadyExists(email);

        Client client = Client.create(firstName, lastName, email);

        clientGateway.save(client);

        return CreateClientUseCaseOutput.from(client);
    }

    private void verifyEmailAlreadyExists(final String email) {
        var emailValidated = Email.from(email);

        if (clientGateway.findByEmail(emailValidated).isPresent()) {
            throw new EmailAlreadyExistsException(String.format("email %s already exists", email));
        }
    }
}
