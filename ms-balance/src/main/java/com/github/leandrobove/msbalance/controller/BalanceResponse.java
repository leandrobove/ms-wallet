package com.github.leandrobove.msbalance.controller;

import java.math.BigDecimal;

public record BalanceResponse(String id, BigDecimal balance) {
}
