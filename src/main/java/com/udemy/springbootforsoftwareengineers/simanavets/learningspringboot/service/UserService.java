package com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.service;

import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.dao.UserDao;
import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.selectAllUsers();
    }

    public Optional<User> getUser(UUID userUid) {
        return userDao.selectUserByUserUid(userUid);
    }

    public int insertUser(User user) {
        return userDao.insertUser(UUID.randomUUID(), user);
    }

    public int removeUser(UUID userUid) {
        Optional<User> optionalUser = getUser(userUid);
        if (optionalUser.isPresent()) {
            userDao.deleteUserByUserUid(userUid);
            return 1;
        }
        return -1;
    }

    public int updateUser(User user) {
        Optional<User> optionalUser = getUser(user.getUserUid());
        if (optionalUser.isPresent()) {
            userDao.updateUser(user);
            return 1;
        }
        return -1;
    }
}
