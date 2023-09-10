package com.financialtransactions.dtos;

import com.financialtransactions.domain.Account;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountDTO{
    private UUID id;
    private String number;
    private UUID userId;
    private BigDecimal balance;
    private Boolean active = Boolean.TRUE;
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.userId = (account.getUser() != null) ? account.getUser().getId() : null;
        this.balance = account.getBalance();
    }

    public AccountDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
