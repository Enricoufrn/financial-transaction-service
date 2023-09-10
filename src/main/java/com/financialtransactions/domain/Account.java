package com.financialtransactions.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne
    private User user;
    private BigDecimal balance = BigDecimal.ZERO;

    public Account() {
    }

    public Account(User user, BigDecimal balance) {
        this.user = user;
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    private void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void addBalance(BigDecimal value) {
        this.setBalance(this.getBalance().add(value));
    }

    public void subtractBalance(BigDecimal value) {
        this.setBalance(this.getBalance().subtract(value));
    }
}
