package com.financialtransactions.exceptions;

public class GenerateTokenException extends Exception {
    private String message;
    private String details;
    public GenerateTokenException(String message) {
        super(message);
        this.message = message;
        this.details = "";
    }
    public GenerateTokenException(String message, String details) {
        super(message);
        this.message = message;
        this.details = details;
    }
    public String getMessage() {
        return message;
    }
    public String getDetails() {
        return details;
    }
}
