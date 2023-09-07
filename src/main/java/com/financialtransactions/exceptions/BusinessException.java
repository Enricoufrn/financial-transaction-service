package com.financialtransactions.exceptions;

public class BusinessException extends GenericCustomException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, String details) {
        super(message, details);
    }
}
