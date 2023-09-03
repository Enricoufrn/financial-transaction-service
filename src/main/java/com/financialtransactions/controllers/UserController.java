package com.financialtransactions.controllers;

import com.financialtransactions.domain.User;
import com.financialtransactions.dtos.UserDTO;
import com.financialtransactions.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/user")
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
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) {
        User savedUser = this.userService.save(user);
        UserDTO userDTO = new UserDTO(savedUser);
        URI uri = uriBuilder.path(this.getByIdPath()).buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }

    @PutMapping(ID)
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody UserDTO user) {
        User updatedUser = this.userService.update(id, user);
        UserDTO userDTO = new UserDTO(updatedUser);
        return ResponseEntity.ok(userDTO);
    }
    @DeleteMapping(ID)
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        this.userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
