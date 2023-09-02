package com.financialtransactions.exceptions;

import com.financialtransactions.enumerations.MessageCode;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String message;
    public BusinessException(String message) {
        super(message);
        this.message = message;
    }
}
