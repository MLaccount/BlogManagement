package com.kickcity.task.blogmanagement.service.impl;

import com.kickcity.task.blogmanagement.common.Utils;
import com.kickcity.task.blogmanagement.common.Validator;
import com.kickcity.task.blogmanagement.exception.NoContentFoundException;
import com.kickcity.task.blogmanagement.exception.ResourceAlreadyExistException;
import com.kickcity.task.blogmanagement.model.User;
import com.kickcity.task.blogmanagement.repository.UserRepository;
import com.kickcity.task.blogmanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Validator validator;

    @Override
    public User findUserById(Long id) {
        validator.checkForNull(id, "UserId");
        logger.info("Fetching User with id {}", id);
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) {
            logger.error("User with id {} not found.", id);
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
        return userOptional.get();
    }

    @Override
    public User findUserByEmail(String email) {
        validator.checkForNull(email, "Email");
        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.error("User with email {} not found.", email);
            throw new EntityNotFoundException("User with email " + email + " not found");
        }
        return user;
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        validator.checkForNull(user, "User");
        validator.checkForNull(user.getEmail(), "Email");
        validator.checkForNull(user.getPassword(), "Password");

        logger.info("Creating User : {}", user);

        if (userRepository.findByEmail(user.getEmail()) != null) {
            logger.error("Unable to create. A User with email {} already exist", user.getEmail());
            throw new ResourceAlreadyExistException("Unable to create. A User with email " +
                    user.getEmail() + " already exist.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getId() == null) {
            user.setCreateDate(Utils.getCurrentDate());
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers(Pageable pageableRequest) {
        Pageable pageable = PageRequest.of(pageableRequest.getPageNumber(), pageableRequest.getPageSize(), Sort.Direction.ASC, "createDate");
        List<User> userList = userRepository.findAll(pageable).getContent();

        if (userList.isEmpty()) {
            throw new NoContentFoundException("No users found");
        }
        return userList;
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        validator.checkForNull(user, "User");
        validator.checkForNull(user.getId(), "UserId");
        // Check that user with given id exists
        User existingUser = findUserById(user.getId());
        // Do not update creation date
        user.setCreateDate(existingUser.getCreateDate());
        return saveUser(user);
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        validator.checkForNull(id, "UserId");
        if (!userRepository.existsById(id)) {
            logger.error("User with id {} not found.", id);
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }

}
