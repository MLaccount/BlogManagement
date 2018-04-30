package com.kickcity.task.blogmanagement.service;

import com.kickcity.task.blogmanagement.model.User;

public interface UserService {

    User findUserById(Long id);

    User findUserByEmail(String email);

}
