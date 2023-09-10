package com.financialtransactions.enumerations;

public enum MessageCode {

    // General
    BAD_REQUEST("bad.request"),
    BAD_CREDENTIALS("bad.credentials"),
    //user
    USER_NOT_FOUND("user.not.found"),
    FINANCIAL_TRANSACTION_NOT_AUTHORIZED("financial.transaction.not.authorized"),
    INTERNAL_SERVER_ERROR("internal.server.error"),
    RESOURCE_NOT_FOUND("resource.not.found"),
    USER_WITH_DOCUMENT_ALREADY_EXISTS("user.with.document.already.exists"),
    USER_WITH_LOGIN_ALREADY_EXISTS("user.with.login.already.exists"),
    USER_WITH_EMAIL_ALREADY_EXISTS("user.with.email.already.exists"),
    //Account
    ACCOUNT_NOT_FOUND("account.not.found"),
    ACCOUNT_MUST_NOT_BE_NULL("account.must.not.be.null"),
    USER_ACCOUNT_MUST_NOT_BE_NULL("user.account.must.not.be.null"),
    ACCOUNT_NUMBER_ALREADY_EXISTS("account.number.already.exists"),
    INVALID_ACCOUNT("invalid.account"),

    // auth
    AUTHORIZATION_HEADER_NOT_FOUND("authorization.header.not.found"),
    TOKEN_NOT_FOUND("token.not.found"),
    INVALID_TOKEN("invalid.token"),
    INVALID_LOGIN("invalid.login"),
    INVALID_PASSWORD("invalid.password"),
    INVALID_CREDENTIALS("invalid.credentials"),
    INVALID_AUTHORIZATION_HEADER("invalid.authorization.header"),
    AUTHENTICATION_NOT_FINALLY("authentication.not.finally");
    private String code;

    MessageCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
