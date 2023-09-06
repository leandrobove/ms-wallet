package com.github.leandrobove.mswallet.domain.exception;

public class CpfAlreadyExistsException extends DomainException {

    public CpfAlreadyExistsException() {
        super();
    }

    public CpfAlreadyExistsException(String message) {
        super(message);
    }

    public CpfAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
