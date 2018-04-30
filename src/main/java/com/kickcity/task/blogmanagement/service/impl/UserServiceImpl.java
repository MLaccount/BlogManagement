package com.kickcity.task.blogmanagement.service.impl;

import com.kickcity.task.blogmanagement.model.User;
import com.kickcity.task.blogmanagement.repository.UserRepository;
import com.kickcity.task.blogmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserById(Long id) {
        Assert.notNull(id, "User id must not be null");
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new EntityNotFoundException("Not found user with ID: " + id));
    }

    @Override
    public User findUserByEmail(String email) {
        Assert.notNull(email, "User email must not be null");
        Optional<User> userOptional = userRepository.findByEm);
        return userOptional.orElseThrow(() -> new EntityNotFoundException("Not found user with ID: " + id));
    }
}
