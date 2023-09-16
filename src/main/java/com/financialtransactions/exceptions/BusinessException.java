package com.financialtransactions.exceptions;

import java.util.List;

public class BusinessException extends GenericCustomException {

    private List<String> messages;

    public BusinessException(String message) {
        super(message);
        this.messages = List.of(message);
    }
    public BusinessException(String message, List<String> messages) {
        super(message);
        this.messages = messages;
    }

    public BusinessException(String message, String details) {
        super(message, details);
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
