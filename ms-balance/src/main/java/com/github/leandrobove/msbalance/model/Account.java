package com.github.leandrobove.msbalance.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Account {

    @EqualsAndHashCode.Include
    private String id;

    private BigDecimal balance;
}
