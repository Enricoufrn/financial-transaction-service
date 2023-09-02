package com.financialtransactions.enumerations;

public enum MessageCode {
    FINANCIAL_TRANSACTION_NOT_AUTHORIZED("financial.transaction.not.authorized");

    private String code;

    MessageCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
