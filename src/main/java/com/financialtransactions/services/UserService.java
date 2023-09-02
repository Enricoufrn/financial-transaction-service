package com.financialtransactions.services;

import com.financialtransactions.domain.User;
import com.financialtransactions.dtos.UserDTO;
import com.financialtransactions.enumerations.MessageCode;
import com.financialtransactions.exceptions.ResourceException;
import com.financialtransactions.helper.MessageHelper;
import com.financialtransactions.repositories.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private IUserRepository userRepository;
    private MessageHelper messageHelper;

    public UserService(IUserRepository userRepository, MessageHelper messageHelper) {
        this.userRepository = userRepository;
        this.messageHelper = messageHelper;
    }
    public User findById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() ->
                new ResourceException(this.messageHelper.getMessage(MessageCode.USER_NOT_FOUND), HttpStatus.NOT_FOUND));
    }
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
    public User save(UserDTO userDto) {
        User user = new User(userDto.name(), userDto.email(), userDto.login(),
                userDto.password(), userDto.document(), userDto.type());
        return this.userRepository.save(user);
    }
}
