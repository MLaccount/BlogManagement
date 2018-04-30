package com.kickcity.task.blogmanagement.controller;

import com.kickcity.task.blogmanagement.exception.NoContentFoundException;
import com.kickcity.task.blogmanagement.exception.ResourceAlreadyExistException;
import com.kickcity.task.blogmanagement.model.Record;
import com.kickcity.task.blogmanagement.model.User;
import com.kickcity.task.blogmanagement.repository.RecordRepository;
import com.kickcity.task.blogmanagement.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecordRepository recordRepository;

    @PostMapping("/user/create")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        logger.info("Creating User : {}", user);

        if (userRepository.existsById(user.getId()) || userRepository.findByEmail(user.getEmail()) != null) {
            logger.error("Unable to create. A User with name {} already exist", user.getEmail());
            throw new ResourceAlreadyExistException("Unable to create. A User with name " +
                    user.getEmail() + " already exist.");
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/user/get/{id}")
    public ResponseEntity<?> getUser(@PathVariable(value = "id") long userId) {
        logger.info("Fetching User with id {}", userId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            logger.error("User with id {} not found.", userId);
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }
        return new ResponseEntity<User>(userOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/user/get")
    public List<User> getUser() {
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            throw new NoContentFoundException("No users found");
        }
        return userList;
    }

    @RequestMapping(value = "/user/update/{id}", method = RequestMethod.PUT)
    public User updateUser(@PathVariable("id") long id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);
        User currentUser = userRepository.getOne(id);

        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(user.getPassword());
        currentUser.setRecords(user.getRecords());
        currentUser.setId(id);

        userRepository.save(currentUser);
        return currentUser;
    }

    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            logger.error("User with id {} not found.", id);
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/records/{id}")
    public List<Record> getUserRecords(@PathVariable(value = "id") long userId) {
        logger.info("Fetching User's records with id {}", userId);
        List<Record> recordList = recordRepository.getRecordsByUserId(userId);

        if (recordList.isEmpty()) {
            logger.error("User with id {} not found.", userId);
            throw new NoContentFoundException("No users found");
        }
        return recordList;
    }
}
