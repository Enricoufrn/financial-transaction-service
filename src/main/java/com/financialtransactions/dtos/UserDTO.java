package com.financialtransactions.dtos;

import com.financialtransactions.domain.User;
import com.financialtransactions.enumerations.UserType;

import java.util.UUID;

public record UserDTO(UUID id, String name, String email, String login, String password, UserType type, String document) {
    public UserDTO(UUID id, String name, String email, String login, String password, UserType type, String document) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.type = type;
        this.document = document;
    }

    public UserDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getLogin(), user.getPassword(), user.getUserType(), user.getDocument());
    }
}
