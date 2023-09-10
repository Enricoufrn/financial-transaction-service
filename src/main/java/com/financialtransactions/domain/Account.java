package com.financialtransactions.domain;

import com.financialtransactions.dtos.AccountDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@SQLDelete(sql = "UPDATE accounts SET active = false WHERE id = ?")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne
    private User user;
    @Column(unique = true)
    @NotBlank(message = "O número da conta é obrigatório.")
    private String number;
    private BigDecimal balance = BigDecimal.ZERO;
    private Boolean active = Boolean.TRUE;

    public Account() {
    }

    public Account(AccountDTO accountDTO, User user) {
        this.user = user;
        this.number = accountDTO.getNumber();
        this.balance = accountDTO.getBalance();
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
