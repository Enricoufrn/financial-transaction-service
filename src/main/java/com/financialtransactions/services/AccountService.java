package com.financialtransactions.services;

import com.financialtransactions.domain.Account;
import com.financialtransactions.domain.User;
import com.financialtransactions.dtos.AccountDTO;
import com.financialtransactions.dtos.DepositDTO;
import com.financialtransactions.enumerations.MessageCode;
import com.financialtransactions.exceptions.BusinessException;
import com.financialtransactions.exceptions.GenericCustomException;
import com.financialtransactions.exceptions.ResourceException;
import com.financialtransactions.helper.MessageHelper;
import com.financialtransactions.repositories.IAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
public class AccountService {
    private IAccountRepository accountRepository;
    private MessageHelper messageHelper;
    private UserService userService;

    public AccountService(IAccountRepository accountRepository, MessageHelper messageHelper, UserService userService) {
        this.accountRepository = accountRepository;
        this.messageHelper = messageHelper;
        this.userService = userService;
    }

    public AccountDTO save(AccountDTO accountDto) {
        validateAccount(accountDto);
        User user = this.userService.findById(accountDto.getUserId());
        Account account = new Account(accountDto, user);
        return new AccountDTO(this.accountRepository.save(account));
    }

    public void delete(UUID id) {
        if (!this.userService.isLoggedUserAdmin()){
            throw new BusinessException(this.messageHelper.getMessage(MessageCode.PERMISSION_DENIED), this.messageHelper.getMessage(MessageCode.ONLY_ADMIN_CAN_DELETE_ACCOUNT));
        }
        this.accountRepository.findById(id).orElseThrow(() ->
                new ResourceException(this.messageHelper.getMessage(MessageCode.ACCOUNT_NOT_FOUND), HttpStatus.NOT_FOUND, "id: "+ id));
        this.accountRepository.deleteById(id);
    }

    public AccountDTO findByUserId(UUID userId) {
        if (!this.userService.isLoggedUserAdmin() && !this.userService.getLoggedUser().getId().equals(userId)){
            throw new BusinessException(this.messageHelper.getMessage(MessageCode.PERMISSION_DENIED));
        }
        return findByUserIdWithoutCheckPermission(userId);
    }
    public AccountDTO findByUserIdWithoutCheckPermission(UUID userId) {
        Account account =  this.accountRepository.findByUserId(userId).orElseThrow(() ->
                new ResourceException(this.messageHelper.getMessage(MessageCode.ACCOUNT_FOR_THIS_USER_NOT_FOUND), HttpStatus.NOT_FOUND, "userId: "+ userId));
        return new AccountDTO(account);
    }
    public AccountDTO findByNumber(String number) {
        if(!this.userService.isLoggedUserAdmin()){
            User loggedUser = this.userService.getLoggedUser();
            String loggedUserAccountNumber = this.getAccountNumberByUserId(loggedUser.getId());
            if (!loggedUserAccountNumber.equals(number)){
                throw new BusinessException(this.messageHelper.getMessage(MessageCode.PERMISSION_DENIED));
            }
        }
        Account account = this.accountRepository.findByNumber(number).orElseThrow(() ->
                new ResourceException(this.messageHelper.getMessage(MessageCode.ACCOUNT_NOT_FOUND), HttpStatus.NOT_FOUND, "number: "+ number));
        return new AccountDTO(account);
    }

    public String getAccountNumberByUserId(UUID userId) {
        return this.accountRepository.findNumberByUserId(userId).orElseThrow(() ->
                new ResourceException(this.messageHelper.getMessage(MessageCode.ACCOUNT_FOR_THIS_USER_NOT_FOUND), HttpStatus.NOT_FOUND, "userId: "+ userId));
    }

    public AccountDTO findById(UUID id) {
        if (!this.userService.isLoggedUserAdmin()){
            throw new BusinessException(this.messageHelper.getMessage(MessageCode.PERMISSION_DENIED), this.messageHelper.getMessage(MessageCode.ONLY_ADMIN_ALLOWED));
        }
        return new AccountDTO(this.getById(id));
    }

    private Account getById(UUID id){
        return this.accountRepository.findById(id).orElseThrow(() ->
                new ResourceException(this.messageHelper.getMessage(MessageCode.ACCOUNT_NOT_FOUND), HttpStatus.NOT_FOUND, "id: "+ id));
    }

    public AccountDTO updateBalance(UUID accountId, BigDecimal value, Boolean subtract){
        if(!this.userService.isLoggedUserAdmin()){
            AccountDTO loggedUserAccount = this.findByUserId(this.userService.getLoggedUser().getId());
            if (!loggedUserAccount.getId().equals(accountId)){
                throw new BusinessException(this.messageHelper.getMessage(MessageCode.PERMISSION_DENIED));
            }
        }
        return updateBalanceWithoutCheckPermission(accountId, value, subtract);
    }

    public AccountDTO updateBalanceWithoutCheckPermission(UUID accountId, BigDecimal value, Boolean subtract){
        Account account = this.getById(accountId);
        if (subtract){
            if(account.getBalance().compareTo(value) < 0){
                throw new BusinessException(this.messageHelper.getMessage(MessageCode.INSUFFICIENT_FUNDS));
            }
            account.subtractBalance(value);
        }else{
            account.addBalance(value);
        }
        Account updatedAccount = this.accountRepository.save(account);
        return new AccountDTO(updatedAccount);
    }

    public AccountDTO deposit(DepositDTO depositDTO){
        if (!this.userService.isLoggedUserAdmin()){
            String loggedUserAccountNumber = this.getAccountNumberByUserId(this.userService.getLoggedUser().getId());
            if (!loggedUserAccountNumber.equals(depositDTO.accountNumber())){
                throw new BusinessException(this.messageHelper.getMessage(MessageCode.PERMISSION_DENIED));
            }
        }
        AccountDTO account = this.findByNumber(depositDTO.accountNumber());
        return this.updateBalance(account.getId(), depositDTO.value(), false);
    }

    public void validateAccount(AccountDTO account){
        if(account == null){
            throw new BusinessException(this.messageHelper.getMessage(MessageCode.INVALID_ACCOUNT), this.messageHelper.getMessage(MessageCode.ACCOUNT_MUST_NOT_BE_NULL));
        }
        if(account.getUserId() == null){
           throw new BusinessException(this.messageHelper.getMessage(MessageCode.INVALID_ACCOUNT), this.messageHelper.getMessage(MessageCode.USER_ACCOUNT_MUST_NOT_BE_NULL));
        }
        this.accountRepository.findByNumber(account.getNumber()).ifPresent(a -> {
            throw new BusinessException(this.messageHelper.getMessage(MessageCode.INVALID_ACCOUNT), this.messageHelper.getMessage(MessageCode.ACCOUNT_NUMBER_ALREADY_EXISTS));
        });
        this.accountRepository.findByUserId(account.getUserId()).ifPresent(a -> {
            throw new BusinessException(this.messageHelper.getMessage(MessageCode.INVALID_ACCOUNT), this.messageHelper.getMessage(MessageCode.ACCOUNT_FOR_THIS_USER_ALREADY_EXISTS));
        });
    }
}
