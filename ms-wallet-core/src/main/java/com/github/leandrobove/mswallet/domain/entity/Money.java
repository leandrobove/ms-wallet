package com.github.leandrobove.mswallet.domain.entity;

import com.github.leandrobove.mswallet.domain.ValueObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Objects;

public final class Money implements ValueObject {

    private final BigDecimal amount;
    private final Currency currency;
    public static final Money ZERO = new Money(BigDecimal.ZERO, null);

    private Money(BigDecimal amount, Currency currency) {
        if (Objects.isNull(amount)) {
            throw new IllegalArgumentException("amount is required");
        }

        this.amount = amount.setScale(2, RoundingMode.HALF_EVEN);
        this.currency = (Objects.isNull(currency)) ? Currency.getInstance("USD") : currency;

        validate();
    }

    private void validate() {
        if (Objects.isNull(this.amount)) {
            throw new IllegalArgumentException("amount is required");
        }
        if (Objects.isNull(this.currency)) {
            throw new IllegalArgumentException("currency is required");
        }
    }

    public static Money from(BigDecimal amount) {
        return new Money(amount, null);
    }

    public static Money from(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

//    public static Money from(BigDecimal amount, String currencyCode) {
//        return new Money(amount, Currency.getInstance(currencyCode));
//    }

    private void verifyIfCurrencyItsEqualTo(Money otherMoney) {
        if (!currency.equals(otherMoney.currency)) {
            throw new IllegalArgumentException("Currencies must be the same");
        }
    }

    public Money add(Money otherMoney) {
        verifyIfCurrencyItsEqualTo(otherMoney);
        return new Money(amount.add(otherMoney.amount), currency);
    }

    public Money add(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        BigDecimal result = this.amount.add(amount);
        return new Money(result, this.currency);
    }

    public Money subtract(Money otherMoney) {
        verifyIfCurrencyItsEqualTo(otherMoney);
        return new Money(amount.subtract(otherMoney.amount), currency);
    }

    public Money subtract(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        BigDecimal result = this.amount.subtract(amount);
        return new Money(result, this.currency);
    }

    public Money multiply(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        return new Money(this.amount.multiply(amount), currency);
    }

    public Money divide(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Amount cannot be zero");
        }
        return new Money(this.amount.divide(amount), currency);
    }

    private Money percentage(BigDecimal percentage) {
        BigDecimal multiplier = percentage.divide(new BigDecimal("100"));
        BigDecimal result = this.amount.multiply(multiplier);
        return new Money(result, this.currency);
    }

    public Money addPercentage(BigDecimal percentage) {
        BigDecimal amountPercentage = this.percentage(percentage).getValue();
        BigDecimal result = this.amount.add(amountPercentage);
        return new Money(result, this.currency);
    }

    public Money subtractPercentage(BigDecimal percentage) {
        BigDecimal amountPercentage = this.percentage(percentage).getValue();
        BigDecimal result = this.amount.subtract(amountPercentage);
        return new Money(result, this.currency);
    }

    public boolean isGreaterThan(Money otherMoney) {
        verifyIfCurrencyItsEqualTo(otherMoney);
        return amount.compareTo(otherMoney.amount) > 0;
    }

    public boolean isLessThan(Money otherMoney) {
        verifyIfCurrencyItsEqualTo(otherMoney);
        return amount.compareTo(otherMoney.amount) < 0;
    }

    public boolean isGreaterThanOrEqualTo(Money otherMoney) {
        verifyIfCurrencyItsEqualTo(otherMoney);
        return amount.compareTo(otherMoney.amount) >= 0;
    }

    public boolean isLessThanOrEqualTo(Money otherMoney) {
        verifyIfCurrencyItsEqualTo(otherMoney);
        return amount.compareTo(otherMoney.amount) <= 0;
    }

    public BigDecimal getValue() {
        return amount;
    }

    public BigDecimal value() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String showFormatted() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setCurrency(currency);
        return formatter.format(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount.equals(money.amount) && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return "Money{" +
                "formatted=" + this.showFormatted() +
                '}';
    }
}
