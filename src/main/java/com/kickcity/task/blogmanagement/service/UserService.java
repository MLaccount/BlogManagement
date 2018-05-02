package com.kickcity.task.blogmanagement.service;

import com.kickcity.task.blogmanagement.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User findUserById(Long id);

    User findUserByEmail(String email);

    User saveUser(User user);

    User updateUser(User user);

    void deleteUserById(Long id);

    List<User> findAllUsers(Pageable pageable);
}
