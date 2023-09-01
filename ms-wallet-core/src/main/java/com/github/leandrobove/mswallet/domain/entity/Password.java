package com.github.leandrobove.mswallet.domain.entity;

import com.github.leandrobove.mswallet.domain.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Password implements ValueObject {

    private static final int MIN_CHARS = 8;
    private static final int MAX_CHARS = 20;
    private static final String ALLOWED_CHARACTERS = "[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]*";

    private final String value;

    public Password(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("password is required");
        }

        if (this.isInvalid(value)) {
            throw new IllegalArgumentException("a valid password is required");
        }

        this.value = value;
    }

    public boolean isValid(final String rawPassword) {
        //check length
        if (rawPassword.length() < MIN_CHARS || rawPassword.length() > MAX_CHARS) {
            return false;
        }

        //check if it has at least one number
        if (!Pattern.compile("[0-9]").matcher(rawPassword).find()) {
            return false;
        }

        //check if it has at least one upper case letter
        if (!Pattern.compile("[a-z]").matcher(rawPassword).find()) {
            return false;
        }

        //check if it has at least one lower case letter
        if (!Pattern.compile("[A-Z]").matcher(rawPassword).find()) {
            return false;
        }

        //check if it has at least one special character
        if (!Pattern.compile("[^a-zA-Z0-9]").matcher(rawPassword).find()) {
            return false;
        }

        //check if it has sequential digits
        if (rawPassword.matches("(012|123|234|345|456|567|678|789)")) {
            return false;
        }

        //check if it has only allowed characters
        if (!rawPassword.matches(ALLOWED_CHARACTERS)) {
            return false;
        }

        return true;
    }

    public boolean isInvalid(final String rawPassword) {
        return !this.isValid(rawPassword);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return value.equals(password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Password{" +
                "value='" + "********" + '\'' +
                '}';
    }
}
