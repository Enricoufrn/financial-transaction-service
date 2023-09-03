package com.financialtransactions.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/authentication-test")
public class AuthenticationTestController {
    @GetMapping("/common-user")
    public String commonUser() {
        return "Se você chegou até aqui é porque você é um usuário comum";
    }
    @GetMapping("/admin-user")
    public String adminUser() {
        return "Se você chegou até aqui é porque você é um usuário administrador";
    }
}
