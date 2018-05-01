package com.kickcity.task.blogmanagement.controller;

import com.kickcity.task.blogmanagement.model.User;
import com.kickcity.task.blogmanagement.model.dto.UserDto;
import com.kickcity.task.blogmanagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        logger.info("Creating User : {}", userDto);
        User user = modelMapper.map(userDto, User.class);

        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserbyId(@PathVariable(value = "id") long userId) {
        logger.info("Fetching User with id {}", userId);
        UserDto userDto = modelMapper.map(userService.findUserById(userId), UserDto.class);

        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    @GetMapping("")
    public List<UserDto> getUsers() {
        List<User> users = userService.findAllUsers();
        return users.stream().map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public User updateUser(@PathVariable("id") long id, @RequestBody UserDto userDto) {
        logger.info("Updating User with id {}", id);
        User user = modelMapper.map(userDto, User.class);
        user.setId(id);
        return userService.updateUser(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/records/{id}")
//    public List<Record> getUserRecords(@PathVariable(value = "id") long userId) {
//        logger.info("Fetching User's records with id {}", userId);
//        List<Record> recordList = recordRepository.getRecordsByUserId(userId);
//
//        if (recordList.isEmpty()) {
//            logger.error("User with id {} not found.", userId);
//            throw new NoContentFoundException("No users found");
//        }
//        return recordList;
//    }
}
