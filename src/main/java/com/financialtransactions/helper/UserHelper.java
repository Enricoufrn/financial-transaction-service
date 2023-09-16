package com.financialtransactions.helper;

import com.financialtransactions.domain.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {
    private final SecurityContext securityContext = SecurityContextHolder.getContext();
    public User getLoggedUser(){
        return (User) this.securityContext.getAuthentication().getPrincipal();
    }
}
