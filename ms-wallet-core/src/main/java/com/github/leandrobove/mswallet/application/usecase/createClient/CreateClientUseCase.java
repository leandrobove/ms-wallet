package com.github.leandrobove.mswallet.application.usecase.createClient;

import com.github.leandrobove.mswallet.application.gateway.ClientGateway;
import com.github.leandrobove.mswallet.domain.entity.CPF;
import com.github.leandrobove.mswallet.domain.entity.Client;
import com.github.leandrobove.mswallet.domain.entity.Email;
import com.github.leandrobove.mswallet.domain.exception.CpfAlreadyExistsException;
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
        var cpf = input.getCpf();

        this.verifyEmailAlreadyExists(email);
        this.verifyCpfAlreadyExists(cpf);

        Client client = Client.create(firstName, lastName, email, cpf);

        clientGateway.save(client);

        return CreateClientUseCaseOutput.from(client);
    }

    private void verifyEmailAlreadyExists(final String email) {
        var emailValidated = Email.from(email);

        clientGateway.findByEmail(emailValidated).ifPresent((client) -> {
            throw new EmailAlreadyExistsException(emailValidated);
        });
    }

    private void verifyCpfAlreadyExists(final String cpf) {
        var cpfValidated = CPF.from(cpf);

        clientGateway.findByCpf(cpfValidated).ifPresent((client) -> {
            throw new CpfAlreadyExistsException(cpfValidated);
        });
    }
}
