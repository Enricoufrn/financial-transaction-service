package com.financialtransactions.domain;

import com.financialtransactions.enumerations.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    @NotBlank
    private String name;
    @NotBlank
    @Column(unique = true)
    @Pattern(regexp = "^(.+)@(.+)$")
    private String email;
    @NotBlank
    @Column(unique = true)
    private String login;
    @NotBlank
    private String password;
    /**
     * This field must be a CPF or CNPJ.
     */
    @NotBlank
    @Column(unique = true)
    private String document;
    @NotBlank
    @Enumerated(EnumType.STRING)
    private UserType userType;

    // Constructors
    public User() {
    }

    public User(String name, String email, String login, String password, String document, UserType userType) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.document = document;
        this.userType = userType;
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
