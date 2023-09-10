package com.financialtransactions.dtos;

import com.financialtransactions.domain.Account;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountDTO (UUID id, String number, UUID userId, BigDecimal balance, Boolean active){
    public AccountDTO(Account account) {
        this(account.getId(), account.getNumber(), account.getUser().getId(), account.getBalance(), account.getActive());
    }

    public AccountDTO(String number, UUID userId, BigDecimal balance) {
        this(null, number, userId, balance, Boolean.TRUE);
    }
}
