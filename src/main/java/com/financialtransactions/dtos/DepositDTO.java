package com.financialtransactions.dtos;

import java.math.BigDecimal;

public record DepositDTO(BigDecimal value, String accountNumber) {
}
