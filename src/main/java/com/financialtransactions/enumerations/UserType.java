package com.financialtransactions.enumerations;
public enum UserType {
    ADMIN("Administrador"),
    COMMON("Usuário comum"),
    SHOPKEEPER("Lojista");
    private String description;
    UserType(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
