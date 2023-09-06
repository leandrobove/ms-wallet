package com.github.leandrobove.mswallet.infrastructure.web.api.exception;

import com.github.leandrobove.mswallet.domain.exception.CpfAlreadyExistsException;
import com.github.leandrobove.mswallet.domain.exception.EmailAlreadyExistsException;
import com.github.leandrobove.mswallet.domain.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest req) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = ex.getMessage();

        return new ResponseEntity<>(new ApiError(status.value(), message, OffsetDateTime.now()), status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest req) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = ex.getMessage();

        return new ResponseEntity<>(new ApiError(status.value(), message, OffsetDateTime.now()), status);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExists(EmailAlreadyExistsException ex, WebRequest req) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = ex.getMessage();

        return new ResponseEntity<>(new ApiError(status.value(), message, OffsetDateTime.now()), status);
    }

    @ExceptionHandler(CpfAlreadyExistsException.class)
    public ResponseEntity<?> handleCpfAlreadyExists(CpfAlreadyExistsException ex, WebRequest req) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = ex.getMessage();

        return new ResponseEntity<>(new ApiError(status.value(), message, OffsetDateTime.now()), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception ex, WebRequest req) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = "Oops! we're facing an internal error, please contact an administrator.";

        log.error(ex.getMessage(), ex);

        ApiError error = new ApiError(status.value(), detail, OffsetDateTime.now());

        return super.handleExceptionInternal(ex, error, new HttpHeaders(), status, req);
    }
}
