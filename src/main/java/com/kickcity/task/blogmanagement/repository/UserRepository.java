package com.kickcity.task.blogmanagement.repository;

import com.kickcity.task.blogmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
