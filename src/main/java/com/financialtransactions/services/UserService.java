package com.financialtransactions.services;

import com.financialtransactions.domain.User;
import com.financialtransactions.dtos.UserDTO;
import com.financialtransactions.enumerations.MessageCode;
import com.financialtransactions.enumerations.Role;
import com.financialtransactions.exceptions.ResourceException;
import com.financialtransactions.helper.MessageHelper;
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

    public UserService(IUserRepository userRepository, MessageHelper messageHelper) {
        this.userRepository = userRepository;
        this.messageHelper = messageHelper;
    }
    public User findById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() ->
                new ResourceException(this.messageHelper.getMessage(MessageCode.USER_NOT_FOUND), HttpStatus.NOT_FOUND, "id: "+ id));
    }
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
    public User save(UserDTO userDto) {
        User user = new User(userDto.name(), userDto.email(), userDto.login(),
                userDto.password(), userDto.document(), userDto.type());
        this.validateUser(user);
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

    public void validateUser(User user){
        this.validateLogin(user.getLogin());
        this.validateEmail(user.getEmail());
        this.validateDocument(user.getDocument(), user.getUserType());
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

    private void updateUser(UserDTO user, User userToUpdate){
        userToUpdate.setName(user.name());
        userToUpdate.setEmail(user.email());
        userToUpdate.setLogin(user.login());
        userToUpdate.setDocument(user.document());
        userToUpdate.setUserType(user.type());
    }
}
