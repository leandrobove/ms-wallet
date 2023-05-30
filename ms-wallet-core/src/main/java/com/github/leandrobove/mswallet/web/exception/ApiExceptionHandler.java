package com.github.leandrobove.mswallet.web.exception;

import com.github.leandrobove.mswallet.exception.EmailAlreadyExistsException;
import com.github.leandrobove.mswallet.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest req) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = ex.getMessage();

        return new ResponseEntity<>(new Error(status.value(), message, OffsetDateTime.now()), status);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest req) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = ex.getMessage();

        return new ResponseEntity<>(new Error(status.value(), message, OffsetDateTime.now()), status);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExists(EmailAlreadyExistsException ex, WebRequest req) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = ex.getMessage();

        return new ResponseEntity<>(new Error(status.value(), message, OffsetDateTime.now()), status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        StringBuilder messageBuilder = new StringBuilder();

        fieldErrors.stream().forEach((fieldError) -> {
            String message = messageSource.getMessage(fieldError, Locale.ENGLISH);
            messageBuilder.append(message + ", ");
        });

        return new ResponseEntity<>(new Error(status.value(), messageBuilder.toString()
                .substring(0, messageBuilder.toString().length() - 2), OffsetDateTime.now()), status);
    }
}
