package com.github.leandrobove.msbalance.api.dto;

import java.math.BigDecimal;

public record BalanceResponse(String id, BigDecimal balance) {
}
