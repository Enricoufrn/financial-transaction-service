package com.financialtransactions.controllers;

import com.financialtransactions.domain.User;
import com.financialtransactions.dtos.UserDTO;
import com.financialtransactions.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController extends GenericController{
    private UserService userService;

    public UserController(UserService userService, UriComponentsBuilder uriBuilder) {
        super(uriBuilder);
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(this.userService.findAll());
    }
    @GetMapping(ID)
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.userService.findById(id));
    }
    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDTO user) {
        User savedUser = this.userService.save(user);
        UserDTO userDTO = new UserDTO(savedUser);
        URI uri = uriBuilder.path(this.getByIdPath()).buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(uri).body(savedUser);
    }
}
