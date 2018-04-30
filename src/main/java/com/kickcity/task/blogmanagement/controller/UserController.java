package com.kickcity.task.blogmanagement.controller;

import com.kickcity.task.blogmanagement.exception.NoContentFoundException;
import com.kickcity.task.blogmanagement.exception.ResourceAlreadyExistException;
import com.kickcity.task.blogmanagement.model.Record;
import com.kickcity.task.blogmanagement.model.User;
import com.kickcity.task.blogmanagement.repository.RecordRepository;
import com.kickcity.task.blogmanagement.repository.UserRepository;
import com.kickcity.task.blogmanagement.service.UserService;
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
@RequestMapping("/api/users")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        logger.info("Creating User : {}", user);
        if (userService.findUserById(user.getId()) != null && userService.findUserByEmail(user.getEmail()) != null) {
            logger.error("Unable to create. A User with name {} already exist", user.getEmail());
            throw new ResourceAlreadyExistException("Unable to create. A User with name " +
                    user.getEmail() + " already exists.");
        }
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable(value = "id") long userId) {
        logger.info("Fetching User with id {}", userId);
        Optional<User> userOptional = userService.findUserById(userId);
        if (!userOptional.isPresent()) {
            logger.error("User with id {} not found.", userId);
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }
        return new ResponseEntity<User>(userOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/list")
    public List<User> getUser() {
        List<User> userList = userService.findAllUsers();
        if (userList.isEmpty()) {
            throw new NoContentFoundException("No users found");
        }
        return userList;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public User updateUser(@PathVariable("id") long id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);
        user.setId(id);
        return userService.updateUser(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /*@GetMapping("/records/{id}")
    public List<Record> getUserRecords(@PathVariable(value = "id") long userId) {
        logger.info("Fetching User's records with id {}", userId);
        List<Record> recordList = recordRepository.getRecordsByUserId(userId);

        if (recordList.isEmpty()) {
            logger.error("User with id {} not found.", userId);
            throw new NoContentFoundException("No users found");
        }
        return recordList;
    }*/
}
