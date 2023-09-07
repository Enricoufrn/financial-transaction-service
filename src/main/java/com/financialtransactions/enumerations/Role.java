package com.financialtransactions.enumerations;
public enum Role {
    ROLE_ADMIN("Administrador"),
    ROLE_COMMON("Usuário comum"),
    ROLE_SHOPKEEPER("Lojista"),
    ROLE_USER("Usuário");
    private String description;
    Role(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
