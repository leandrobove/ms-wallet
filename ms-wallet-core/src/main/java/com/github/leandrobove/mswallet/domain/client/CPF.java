package com.github.leandrobove.mswallet.domain.client;

import com.github.leandrobove.mswallet.domain.ValueObject;

import java.util.Objects;

public final class CPF implements ValueObject {

    private final String value;

    private CPF(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("cpf is required");
        }

        //clean if it is formatted
        if (this.isFormatted(value)) {
            value = this.cleanCPF(value);
        }

        //check if it is valid
        if (this.isInvalid(value)) {
            throw new IllegalArgumentException("a valid CPF is required");
        }

        this.value = value;
    }

    public static CPF from(final String cpf) {
        return new CPF(cpf);
    }

    public boolean isFormatted(final String cpf) {
        return cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    }

    public String cleanCPF(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    private boolean isValid(final String cpf) {
        int[] numeros = new int[11];
        int[] pesoA = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesoB = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        if (cpf.length() != 11 || cpf.chars().allMatch(n -> n == cpf.charAt(0))) {
            return false;
        }

        for (int i = 0; i < 11; i++) {
            numeros[i] = Integer.parseInt(cpf.substring(i, i + 1));
        }

        int somaA = 0;
        for (int i = 0; i < pesoA.length; i++) {
            somaA += numeros[i] * pesoA[i];
        }

        int somaB = 0;
        for (int i = 0; i < pesoB.length; i++) {
            somaB += numeros[i] * pesoB[i];
        }

        int digitoA = somaA % 11 < 2 ? 0 : 11 - somaA % 11;
        int digitoB = somaB % 11 < 2 ? 0 : 11 - somaB % 11;

        return digitoA == numeros[9] && digitoB == numeros[10];
    }

    private boolean isInvalid(String cpf) {
        return !this.isValid(cpf);
    }

    public String format() {
        return value.substring(0, 3) + "." + value.substring(3, 6) + "." +
                value.substring(6, 9) + "-" + value.substring(9);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPF cpf = (CPF) o;
        return value.equals(cpf.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "CPF{" +
                "value='" + this.format() + '\'' +
                '}';
    }
}
