package com.financialtransactions.services;

import com.financialtransactions.domain.Account;
import com.financialtransactions.domain.User;
import com.financialtransactions.dtos.AccountDTO;
import com.financialtransactions.dtos.FinancialTransactionDTO;
import com.financialtransactions.enumerations.MessageCode;
import com.financialtransactions.enumerations.Role;
import com.financialtransactions.exceptions.BusinessException;
import com.financialtransactions.helper.MessageHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FinancialTransactionValidator {
    private final UserService userService;
    private final AccountService accountService;
    private final MessageHelper messageHelper;

    public FinancialTransactionValidator(UserService userService, AccountService accountService, MessageHelper messageHelper) {
        this.userService = userService;
        this.accountService = accountService;
        this.messageHelper = messageHelper;
    }

    public void validateFinancialTransaction(FinancialTransactionDTO financialTransaction){
        List<String> messages = new ArrayList<>();
        User sender = this.userService.findById(financialTransaction.senderId());
        User receiver = this.userService.findById(financialTransaction.receiverId());
        if (sender.getRole().equals(Role.ROLE_SHOPKEEPER)){
            throw new BusinessException(this.messageHelper.getMessage(MessageCode.SHOPKEEPER_USERS_CANNOT_CARRY_OUT_FINANCIAL_TRANSACTIONS));
        }
        if(sender.getId().equals(receiver.getId())){
            messages.add(this.messageHelper.getMessage(MessageCode.SENDER_MUST_NOT_EQUALS_RECEIVER));
        }
        AccountDTO senderAccount = this.accountService.findByUserId(sender.getId());
        if(senderAccount.getBalance().compareTo(financialTransaction.value()) < 0){
            messages.add(this.messageHelper.getMessage(MessageCode.INSUFFICIENT_FUNDS));
        }
        if(!messages.isEmpty()){
            throw new BusinessException("", messages);
        }
    }
}
