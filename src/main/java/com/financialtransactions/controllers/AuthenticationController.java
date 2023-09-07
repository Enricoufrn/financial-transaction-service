package com.financialtransactions.controllers;

import com.financialtransactions.domain.User;
import com.financialtransactions.dtos.AuthenticationRequestDTO;
import com.financialtransactions.dtos.UserDTO;
import com.financialtransactions.exceptions.GenerateTokenException;
import com.financialtransactions.services.IAuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;

    public AuthenticationController(IAuthenticationService authenticationService, UserDetailsService userDetailsService) {
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO authenticationRequestDTO, HttpServletResponse response) throws ServletException {
        Authentication authentication = this.authenticationService.authenticate(authenticationRequestDTO.login(), authenticationRequestDTO.password());
        UserDTO userDTO = new UserDTO((User) authentication.getPrincipal());
        this.authenticationService.addJwtTokenToHeader(this.userDetailsService.loadUserByUsername(userDTO.login()), response);
        return ResponseEntity.ok().body(userDTO);
    }
    @GetMapping("/common-user")
    public String commonUser() {
        return "Se você chegou até aqui é porque você é um usuário comum";
    }
    @GetMapping("/admin-user")
    public String adminUser() {
        return "Se você chegou até aqui é porque você é um usuário administrador";
    }
    @GetMapping("/shopkeeper-user")
    public String shopkeeperUser() {
        return "Se você chegou até aqui é porque você é um usuário logista";
    }
}
