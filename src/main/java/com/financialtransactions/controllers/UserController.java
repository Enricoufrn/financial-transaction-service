package com.financialtransactions.controllers;

import com.financialtransactions.domain.User;
import com.financialtransactions.dtos.UserDTO;
import com.financialtransactions.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends GenericController{
    private final UserService userService;

    public UserController(UserService userService, UriBuilder uriBuilder) {
        super(uriBuilder);
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<User> users = this.userService.findAll();
        List<UserDTO> userDTOS = users.stream().map(UserDTO::new).toList();
        return ResponseEntity.ok(userDTOS);
    }
    @GetMapping(ID)
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        User user = this.userService.findById(id);
        UserDTO userDTO = new UserDTO(user);
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) {
        User savedUser = this.userService.save(user);
        UserDTO userDTO = new UserDTO(savedUser);
        URI uri = this.uriBuilder.replacePath(this.getByIdPath()).build(savedUser.getId());
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
