package com.financialtransactions.dtos;

import com.financialtransactions.domain.User;
import com.financialtransactions.enumerations.Role;

import java.util.UUID;

public record UserDTO(UUID id, String name, String email, String login, String password, String role, String document) {
    public UserDTO(String name, String email, String login, String password, String role, String document) {
        this(null, name, email, login, password, role, document);
    }

    public UserDTO(UUID id, String name, String email, String login, String password, String role, String document) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role;
        this.document = document;
    }

    public UserDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getLogin(), "*********", user.getRole().name(), user.getDocument());
    }
}
