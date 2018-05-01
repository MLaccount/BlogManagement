package com.kickcity.task.blogmanagement.service;

import com.kickcity.task.blogmanagement.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findUserById(Long id);

    User findUserByEmail(String email);

    User saveUser(User user);

    List<User> findAllUsers();

    User updateUser(User user);

    void deleteUserById(Long id);

}
