package com.financialtransactions.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractLogin(String token);
    String generateToken(UserDetails userDetails);
    boolean isValidToken(String token, UserDetails userDetails);
}
