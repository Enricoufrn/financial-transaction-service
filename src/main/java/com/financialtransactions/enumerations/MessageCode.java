package com.financialtransactions.enumerations;

public enum MessageCode {
    //user
    USER_NOT_FOUND("user.not.found"),
    FINANCIAL_TRANSACTION_NOT_AUTHORIZED("financial.transaction.not.authorized"),
    INTERNAL_SERVER_ERROR("internal.server.error"),
    RESOURCE_NOT_FOUND("resource.not.found"),
    USER_WITH_DOCUMENT_ALREADY_EXISTS("user.with.document.already.exists"),
    USER_WITH_LOGIN_ALREADY_EXISTS("user.with.login.already.exists"),
    USER_WITH_EMAIL_ALREADY_EXISTS("user.with.email.already.exists");

    private String code;

    MessageCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
