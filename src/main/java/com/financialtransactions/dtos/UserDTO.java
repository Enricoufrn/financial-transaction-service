package com.financialtransactions.dtos;

import com.financialtransactions.enumerations.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserDTO(UUID id, @NotBlank String name, @NotBlank String email, @NotBlank String login, @NotBlank String password, @NotNull UserType type, @NotBlank String document) {
}
