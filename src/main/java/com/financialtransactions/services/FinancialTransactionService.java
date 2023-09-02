package com.financialtransactions.services;

import com.financialtransactions.repositories.IFinancialTransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class FinancialTransactionService {
    private IFinancialTransactionRepository financialTransactionRepository;

    public FinancialTransactionService(IFinancialTransactionRepository financialTransactionRepository) {
        this.financialTransactionRepository = financialTransactionRepository;
    }
}