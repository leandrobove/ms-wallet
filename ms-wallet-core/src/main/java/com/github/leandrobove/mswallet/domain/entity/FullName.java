package com.github.leandrobove.mswallet.domain.entity;

import com.github.leandrobove.mswallet.domain.ValueObject;

import java.util.Objects;

public final class FullName implements ValueObject {

    private final String firstName;
    private final String lastName;

    public FullName(String firstName, String lastName) {
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

    public boolean isValid(final String firstName, final String lastName) {
        if (firstName.length() < 2) {
            return false;
        }
        if (lastName.length() < 2) {
            return false;
        }

        return true;
    }

    public boolean isInvalid(final String firstName, final String lastName) {
        return !this.isValid(firstName, lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
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
                "value='" + this.getFullName() + '\'' +
                '}';
    }
}
