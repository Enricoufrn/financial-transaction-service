package com.financialtransactions.exceptions;

import org.springframework.http.HttpStatusCode;

public class ResourceException extends RuntimeException{
    private HttpStatusCode httpStatusCode;
    private String message;

    public ResourceException(String message, HttpStatusCode httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
}
