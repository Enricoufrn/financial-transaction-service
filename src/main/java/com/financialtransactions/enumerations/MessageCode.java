package com.financialtransactions.enumerations;

public enum MessageCode {

    // General
    BAD_REQUEST("bad.request"),
    BAD_CREDENTIALS("bad.credentials"),
    //user
    INVALID_USER("invalid.user"),
    USER_NOT_FOUND("user.not.found"),
    FINANCIAL_TRANSACTION_NOT_AUTHORIZED("financial.transaction.not.authorized"),
    INTERNAL_SERVER_ERROR("internal.server.error"),
    RESOURCE_NOT_FOUND("resource.not.found"),
    USER_WITH_DOCUMENT_ALREADY_EXISTS("user.with.document.already.exists"),
    USER_WITH_LOGIN_ALREADY_EXISTS("user.with.login.already.exists"),
    USER_WITH_EMAIL_ALREADY_EXISTS("user.with.email.already.exists"),
    USER_MUST_NOT_BE_NULL("user.must.not.be.null"),
    USER_ROLE_MUST_NOT_BE_NULL("user.role.must.not.be.null"),
    //Account
    ACCOUNT_NOT_FOUND("account.not.found"),
    ACCOUNT_MUST_NOT_BE_NULL("account.must.not.be.null"),
    USER_ACCOUNT_MUST_NOT_BE_NULL("user.account.must.not.be.null"),
    ACCOUNT_NUMBER_ALREADY_EXISTS("account.number.already.exists"),
    ACCOUNT_FOR_THIS_USER_ALREADY_EXISTS("account.for.this.user.already.exists"),
    INVALID_ACCOUNT("invalid.account"),
    ACCOUNT_FOR_THIS_USER_NOT_FOUND("account.for.this.user.not.found"),

    // auth
    AUTHORIZATION_HEADER_NOT_FOUND("authorization.header.not.found"),
    TOKEN_NOT_FOUND("token.not.found"),
    INVALID_TOKEN("invalid.token"),
    INVALID_LOGIN("invalid.login"),
    INVALID_PASSWORD("invalid.password"),
    INVALID_CREDENTIALS("invalid.credentials"),
    INVALID_AUTHORIZATION_HEADER("invalid.authorization.header"),
    AUTHENTICATION_NOT_FINALLY("authentication.not.finally"),
    // FinancialTransaction
    SHOPKEEPER_USERS_CANNOT_CARRY_OUT_FINANCIAL_TRANSACTIONS("shopkeeper.users.cannot.carry.out.financial.transactions"),
    SENDER_MUST_NOT_EQUALS_RECEIVER("sender.must.not.equals.receiver"),
    INSUFFICIENT_FUNDS("insufficient.funds"),
    ;
    private String code;

    MessageCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
