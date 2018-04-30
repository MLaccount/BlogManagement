package com.kickcity.task.blogmanagement.service.impl;

import com.kickcity.task.blogmanagement.common.Utils;
import com.kickcity.task.blogmanagement.model.User;
import com.kickcity.task.blogmanagement.repository.UserRepository;
import com.kickcity.task.blogmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findUserById(Long id) {
        Assert.notNull(id, "User id must not be null");
        return userRepository.findById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        Assert.notNull(email, "User email must not be null");
        return userRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public void saveUser(User user) {
        Assert.notNull(user, "User must not be null");
        Assert.isNull(user.getId(), "New user must be provided at creation");
        user.setCreateDate(Utils.getCurrentDate());
        userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        //всех зарегистрировавшихся юзеров в порядке регистрации
        //TODO add pagination
        return userRepository.findAll(new Sort(Sort.Direction.DESC, "createDate"));
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        Assert.notNull(user, "user must not be null");
        Assert.notNull(user.getId(), "Existing user must be provided at update");
        // Check that user with given id exists
        User existingUser = findUserById(user.getId()).get();
        // Do not update creation date
        user.setCreateDate(existingUser.getCreateDate());
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        Assert.notNull(id, "User id must not be null");
        userRepository.deleteById(id);
    }

}
