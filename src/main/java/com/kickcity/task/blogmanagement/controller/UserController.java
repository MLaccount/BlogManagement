package com.kickcity.task.blogmanagement.controller;

import com.kickcity.task.blogmanagement.model.Record;
import com.kickcity.task.blogmanagement.model.User;
import com.kickcity.task.blogmanagement.service.RecordService;
import com.kickcity.task.blogmanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RecordService recordService;


    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        logger.info("Creating User : {}", user);
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(value = "id") long userId) {
        logger.info("Fetching User with id {}", userId);
        User user = userService.findUserById(userId);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("")
    public List<User> getUsers(Pageable pageable) {
        return userService.findAllUsers(pageable);
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

    @PostMapping("/{userId}/record")
    public ResponseEntity<?> createRecord(@PathVariable("userId") Long id, @RequestBody Record record) {
        logger.info("Creating Record : {}", record);

        User user = userService.findUserById(id);
        record.setUser(user);
        recordService.saveRecord(record);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }


}
