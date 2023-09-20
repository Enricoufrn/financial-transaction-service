package com.financialtransactions.services;

import com.financialtransactions.domain.FinancialTransaction;
import com.financialtransactions.domain.User;
import com.financialtransactions.dtos.AccountDTO;
import com.financialtransactions.dtos.FinancialTransactionDTO;
import com.financialtransactions.enumerations.MessageCode;
import com.financialtransactions.exceptions.BusinessException;
import com.financialtransactions.helper.MessageHelper;
import com.financialtransactions.repositories.IFinancialTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FinancialTransactionService {
    private final IFinancialTransactionRepository financialTransactionRepository;
    private final UserService userService;
    private final MessageHelper messageHelper;
    private final AccountService accountService;
    private final FinancialTransactionValidator financialTransactionValidator;

    public FinancialTransactionService(IFinancialTransactionRepository financialTransactionRepository, UserService userService,
                                       MessageHelper messageHelper, AccountService accountService) {
        this.financialTransactionRepository = financialTransactionRepository;
        this.userService = userService;
        this.messageHelper = messageHelper;
        this.accountService = accountService;
        this.financialTransactionValidator = new FinancialTransactionValidator(this.userService, this.accountService, this.messageHelper);
    }

    public FinancialTransactionDTO create(FinancialTransactionDTO financialTransaction) {
        this.financialTransactionValidator.validateFinancialTransaction(financialTransaction);
        User sender = this.userService.findById(financialTransaction.senderId());
        User receiver = this.userService.findById(financialTransaction.receiverId());
        FinancialTransaction newFinancialTransaction = new FinancialTransaction(sender, receiver, financialTransaction.value());
        FinancialTransaction financialTransactionSaved = this.save(newFinancialTransaction);
        AccountDTO senderAccount = this.accountService.findByUserId(financialTransaction.senderId());
        AccountDTO receiverAccount = this.accountService.findByUserIdWithoutCheckPermission(financialTransaction.receiverId());
        this.accountService.updateBalance(senderAccount.getId(), financialTransaction.value(), true);
        this.accountService.updateBalanceWithoutCheckPermission(receiverAccount.getId(), financialTransaction.value(), false);
        return new FinancialTransactionDTO(financialTransactionSaved.getId(), financialTransactionSaved.getSender().getId(),
                financialTransactionSaved.getReceiver().getId(), financialTransactionSaved.getValue());
    }

    public List<FinancialTransactionDTO> getFinancialTransactionsHistory(UUID userId) {
        if (!this.userService.isLoggedUserAdmin() && !this.userService.getLoggedUser().getId().equals(userId)){
            throw new BusinessException(this.messageHelper.getMessage(MessageCode.PERMISSION_DENIED));
        }
        return this.financialTransactionRepository.findAllByUserId(userId).stream().map(financialTransaction -> new FinancialTransactionDTO(financialTransaction.getId(), financialTransaction.getSender().getId(),
                financialTransaction.getReceiver().getId(), financialTransaction.getValue())).toList();
    }

    public FinancialTransaction save(FinancialTransaction financialTransaction) {
        return this.financialTransactionRepository.save(financialTransaction);
    }
}
