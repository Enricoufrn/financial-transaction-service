package com.financialtransactions.exceptions;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ResourceException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;
    private String details;

    public ResourceException(String message, HttpStatus httpStatus, String details) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
        this.details = details;
    }
    public ResourceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return this.details;
    }
}
