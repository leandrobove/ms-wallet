package com.github.leandrobove.mswallet.domain.exception;

import com.github.leandrobove.mswallet.domain.entity.Email;

public class EmailAlreadyExistsException extends DomainException {

    private static final String DEFAULT_MESSAGE = "email %s already exists";

    public EmailAlreadyExistsException(Email email) {
        super(String.format(DEFAULT_MESSAGE, email.value()));
    }

    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
