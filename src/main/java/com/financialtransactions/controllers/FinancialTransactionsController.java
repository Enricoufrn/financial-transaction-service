package com.financialtransactions.controllers;

import com.financialtransactions.dtos.FinancialTransactionDTO;
import com.financialtransactions.services.FinancialTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;

@RestController
@RequestMapping("/api/v1/financial-transactions")
public class FinancialTransactionsController extends GenericController{
    private final FinancialTransactionService financialTransactionService;

    public FinancialTransactionsController(FinancialTransactionService financialTransactionService, UriBuilder uriBuilder) {
        super(uriBuilder);
        this.financialTransactionService = financialTransactionService;
    }

    @PostMapping
    public ResponseEntity<?> newFinancialTransaction(@RequestBody FinancialTransactionDTO financialTransactionDTO) {
        FinancialTransactionDTO savedFinancialTransactionDTO = this.financialTransactionService.create(financialTransactionDTO);
        return ResponseEntity.ok(savedFinancialTransactionDTO);
    }
}
