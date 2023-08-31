package com.github.leandrobove.mswallet.domain.entity;

import com.github.leandrobove.mswallet.domain.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Email implements ValueObject {

    private final String value;

    private Email(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("email address is required");
        }
        if (this.isInvalid(value)) {
            throw new IllegalArgumentException("a valid email address is required");
        }

        this.value = value;
    }

    public static Email from(final String email) {
        return new Email(email);
    }

    public String value() {
        return value;
    }

    public boolean isValid(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(regex);

        if (!pattern.matcher(email).matches()) {
            return false;
        }

        if (email.contains("..") || email.contains(".@") || email.contains("@.")) {
            return false;
        }

        String specialChars = "!#$%&'*+/=?^_`{|}~";
        for (int i = 0; i < specialChars.length(); i++) {
            if (email.indexOf(specialChars.charAt(i)) != -1) {
                return false;
            }
        }

        String[] parts = email.split("@");
        String emailProvider = parts[1];
        if (!isAValidEmailProvider(emailProvider)) {
            return false;
        }

        return true;
    }

    public boolean isInvalid(String email) {
        return !this.isValid(email);
    }

    private boolean isAValidEmailProvider(String emailProvider) {
        String[] validDomains = {"gmail.com", "hotmail.com", "yahoo.com", "outlook.com"};

        for (String d : validDomains) {
            if (emailProvider.equalsIgnoreCase(d)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return value.equals(email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Email{" +
                "value='" + value + '\'' +
                '}';
    }
}
