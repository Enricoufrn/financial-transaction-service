package com.financialtransactions.helper;

import com.financialtransactions.enumerations.MessageCode;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageHelper {
    private final MessageSource messageSource;
    public MessageHelper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    public String getMessage(MessageCode code) {
        return messageSource.getMessage(code.getCode(), null, LocaleContextHolder.getLocale());
    }
}
