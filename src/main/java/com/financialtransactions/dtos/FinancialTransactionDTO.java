package com.financialtransactions.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record FinancialTransactionDTO(UUID id, UUID senderId, UUID receiverId, BigDecimal value) {
    public FinancialTransactionDTO(UUID senderId, UUID receiverId, BigDecimal value) {
        this(null, senderId, receiverId, value);
    }
}
