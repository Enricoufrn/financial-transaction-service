package com.financialtransactions.helper;

import com.financialtransactions.domain.User;
import com.financialtransactions.enumerations.Role;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {
    public User getLoggedUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    public boolean isLoggedUserAdmin(){
        return this.getLoggedUser().getRole().equals(Role.ROLE_ADMIN);
    }
}
