package com.financialtransactions.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "financial_transactions")
public class FinancialTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "user_receiver_id")
    private User receiver;
    private BigDecimal value;
    private Boolean reversed;

    // Constructors
    public FinancialTransaction() {
        this.reversed = false;
    }
    public FinancialTransaction(UUID id, User sender, User receiver, BigDecimal value) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.value = value;
        this.reversed = false;
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Boolean getReversed() {
        return reversed;
    }

    public void setReversed(Boolean reversed) {
        this.reversed = reversed;
    }
}
