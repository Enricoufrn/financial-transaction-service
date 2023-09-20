package com.financialtransactions.services;

import com.financialtransactions.domain.User;
import com.financialtransactions.dtos.UserDTO;
import com.financialtransactions.enumerations.MessageCode;
import com.financialtransactions.enumerations.Role;
import com.financialtransactions.exceptions.BusinessException;
import com.financialtransactions.exceptions.ResourceException;
import com.financialtransactions.helper.MessageHelper;
import com.financialtransactions.helper.PasswordHelper;
import com.financialtransactions.helper.UserHelper;
import com.financialtransactions.repositories.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {
    private final IUserRepository userRepository;
    private final MessageHelper messageHelper;
    private final PasswordHelper passwordHelper;
    private final UserHelper userHelper;

    public UserService(IUserRepository userRepository, MessageHelper messageHelper, PasswordHelper passwordHelper, UserHelper userHelper) {
        this.userRepository = userRepository;
        this.messageHelper = messageHelper;
        this.passwordHelper = passwordHelper;
        this.userHelper = userHelper;
    }
    public User findById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() ->
                new ResourceException(this.messageHelper.getMessage(MessageCode.USER_NOT_FOUND), HttpStatus.NOT_FOUND, "id: "+ id));
    }
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
    public User save(UserDTO userDto) {
        User user = new User(userDto.id(), userDto.name(), userDto.email(), userDto.login(),
                userDto.password(), userDto.document(), Role.getRole(userDto.role()));
        this.validateUser(user);
        processPassword(user);
        return this.userRepository.save(user);
    }

    public User update(UUID id, UserDTO user){
        User userToUpdate = this.findById(id);
        this.updateUser(user, userToUpdate);
        return this.userRepository.save(userToUpdate);
    }
    public void delete(UUID id) {
        this.userRepository.findById(id).orElseThrow(() ->
                new ResourceException(this.messageHelper.getMessage(MessageCode.USER_NOT_FOUND), HttpStatus.NOT_FOUND, "id: "+ id));
        this.userRepository.deleteById(id);
    }

    public User findByLogin(String login) {
        return this.userRepository.findByLogin(login).orElseThrow(() ->
                new ResourceException(this.messageHelper.getMessage(MessageCode.USER_NOT_FOUND), HttpStatus.NOT_FOUND, "login: "+ login));
    }

    public User getLoggedUser(){
        return this.userHelper.getLoggedUser();
    }
    public boolean isLoggedUserAdmin(){
        return this.userHelper.isLoggedUserAdmin();
    }

    public void validateUser(User user){
        if (user == null) throw new BusinessException(this.messageHelper.getMessage(MessageCode.USER_MUST_NOT_BE_NULL), "");
        if (user.getRole() == null) throw new BusinessException(this.messageHelper.getMessage(MessageCode.INVALID_USER), this.messageHelper.getMessage(MessageCode.USER_ROLE_MUST_NOT_BE_NULL));
        this.validateLogin(user.getLogin());
        this.validateEmail(user.getEmail());
        this.validateDocument(user.getDocument(), user.getRole());
    }

    private void validateLogin(String login) {
        this.userRepository.findByLogin(login).ifPresent(user -> {
            throw new ResourceException(this.messageHelper.getMessage(MessageCode.USER_WITH_LOGIN_ALREADY_EXISTS), HttpStatus.BAD_REQUEST, "login: "+ login);
        });
    }
    private void validateEmail(String email) {
        this.userRepository.findByEmail(email).ifPresent(user -> {
            throw new ResourceException(this.messageHelper.getMessage(MessageCode.USER_WITH_EMAIL_ALREADY_EXISTS), HttpStatus.BAD_REQUEST, "email: "+ email);
        });
    }
    private void validateDocument(String document, Role role) {
        String documentType = role.equals(Role.ROLE_COMMON) ? "CPF: " : "CNPJ: ";
        this.userRepository.findByDocument(document).ifPresent(user -> {
            throw new ResourceException(this.messageHelper.getMessage(MessageCode.USER_WITH_DOCUMENT_ALREADY_EXISTS), HttpStatus.BAD_REQUEST, documentType+document);
        });
    }

    /**
     * This method verify if is needed to encrypt the password.
     * @param user The user.
     */
    public void processPassword(User user){
        if(user != null){
            if(user.getId() == null){
                // new user
                user.setPassword(passwordHelper.generatePassword(user.getPassword()));
            }else{
                // update user password
                User saved = findById(user.getId());
                if (saved != null){
                    if (!user.getPassword().isEmpty()){
                        String encryptedPassword = passwordHelper.generatePassword(user.getPassword());
                        if (!saved.getPassword().equals(encryptedPassword)){
                            user.setPassword(encryptedPassword);
                        }
                    }else user.setPassword(saved.getPassword());
                }else throw new ResourceException(this.messageHelper.getMessage(MessageCode.USER_NOT_FOUND), HttpStatus.NOT_FOUND, "id: "+ user.getId());
            }
        }
    }

    private void updateUser(UserDTO user, User userToUpdate){
        userToUpdate.setName(user.name());
        userToUpdate.setEmail(user.email());
        userToUpdate.setLogin(user.login());
        userToUpdate.setDocument(user.document());
        userToUpdate.setRole(Role.getRole(user.role()));
    }
}
