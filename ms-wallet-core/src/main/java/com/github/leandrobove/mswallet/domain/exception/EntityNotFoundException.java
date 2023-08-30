package com.github.leandrobove.mswallet.domain.exception;

public class EntityNotFoundException extends DomainException {

    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
