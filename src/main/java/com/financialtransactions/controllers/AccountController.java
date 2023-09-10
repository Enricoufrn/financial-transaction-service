package com.financialtransactions.controllers;

import com.financialtransactions.dtos.AccountDTO;
import com.financialtransactions.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Controller
@RequestMapping("/api/v1/accounts")
public class AccountController extends GenericController{
    private AccountService accountService;
    public AccountController(AccountService accountService, UriBuilder uriBuilder) {
        super(uriBuilder);
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable UUID id) {
        AccountDTO accountDTO = this.accountService.findById(id);
        return ResponseEntity.ok(accountDTO);
    }
    @GetMapping("/number/{number}")
    public ResponseEntity<AccountDTO> getAccountByNumber(@PathVariable(name = "number") String number) {
        AccountDTO accountDTO = this.accountService.findByNumber(number);
        return ResponseEntity.ok(accountDTO);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<AccountDTO> getAccountByUser(@PathVariable(name = "id") UUID userId) {
        AccountDTO accountDTO = this.accountService.findByUserId(userId);
        return ResponseEntity.ok(accountDTO);
    }

    @PostMapping
    public ResponseEntity<AccountDTO> saveAccount(@RequestBody AccountDTO accountDTO) {
        AccountDTO savedAccountDTO = this.accountService.save(accountDTO);
        URI uri = this.uriBuilder.replacePath(this.getByIdPath()).build(savedAccountDTO.getId());
        return ResponseEntity.created(uri).body(savedAccountDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable UUID id) {
        this.accountService.delete(id);
        return ResponseEntity.ok().build();
    }
}
