package com.financialtransactions.helper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Component that helps to generate passwords.
 *
 * @author Enrico Luigi
 */
@Component
public class PasswordHelper {
    private final PasswordEncoder encoder;

    public PasswordHelper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Generates a password using the login and the password as the base of the encryption
     */
    public String generatePassword(String password) {
        return encoder.encode(password);
    }

    /**
     * Generates a token for the user to reset the password
     *
     * @param email The email of the user
     */
    public String generateToken(String email) {
        return encoder.encode(email);
    }
}