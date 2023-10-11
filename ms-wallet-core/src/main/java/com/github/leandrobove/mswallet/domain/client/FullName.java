package com.github.leandrobove.mswallet.domain.client;

import com.github.leandrobove.mswallet.domain.ValueObject;

import java.util.Objects;

public final class FullName implements ValueObject {

    private final String firstName;
    private final String lastName;

    private static final String ALLOWED_CHARACTERS = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$";
    private static final int MIN_CHARS = 2;

    private FullName(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName is required");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName is required");
        }

        //remove whitespace at the beginning and end of the name
        firstName = firstName.trim();
        lastName = lastName.trim();

        if (this.isInvalid(firstName, lastName)) {
            throw new IllegalArgumentException("a valid name is required");
        }


        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static FullName from(final String firstName, final String lastName) {
        return new FullName(firstName, lastName);
    }

    public static FullName from(final String fullName) {
        String[] nameSplit = fullName.split("\\s+");

        var firstName = nameSplit[0];
        var lastName = nameSplit[1];

        return new FullName(firstName, lastName);
    }

    public boolean isValid(final String firstName, final String lastName) {
        if (firstName.length() < MIN_CHARS || lastName.length() < MIN_CHARS) {
            return false;
        }

        //check if it has only allowed characters
        if (!firstName.matches(ALLOWED_CHARACTERS) || !lastName.matches(ALLOWED_CHARACTERS)) {
            return false;
        }

        return true;
    }

    public boolean isInvalid(final String firstName, final String lastName) {
        return !this.isValid(firstName, lastName);
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String fullName() {
        return this.firstName() + " " + this.lastName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return firstName.equals(fullName.firstName) && lastName.equals(fullName.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return "FullName{" +
                "value='" + this.fullName() + '\'' +
                '}';
    }
}
