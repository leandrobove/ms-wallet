package com.github.leandrobove.mswallet.domain.exception;

import com.github.leandrobove.mswallet.domain.entity.CPF;

public class CpfAlreadyExistsException extends DomainException {

    private static final String DEFAULT_MESSAGE = "cpf %s already exists";

    public CpfAlreadyExistsException(CPF cpf) {
        super(String.format(DEFAULT_MESSAGE, cpf.format()));
    }

    public CpfAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
