package com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.service;

import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.dao.UserDao;
import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.model.User.Gender;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers(Optional<String> gender) {
        List<User> users = userDao.selectAllUsers();
        if (!gender.isPresent()) {
            return users;
        }
        try {
            Gender theGender = Gender.valueOf(gender.get().toUpperCase());
            return users.stream()
                    .filter(user -> user.getGender().equals(theGender))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalStateException("Invalid gender", e);
        }
    }

    public Optional<User> getUser(UUID userUid) {
        return userDao.selectUserByUserUid(userUid);
    }

    public int insertUser(User user) {
        UUID userUid = user.getUserUid() == null ? UUID.randomUUID() : user.getUserUid();
        return userDao.insertUser(userUid, User.newUser(userUid, user));
    }

    public int removeUser(UUID userUid) {
        Optional<User> optionalUser = getUser(userUid);
        if (optionalUser.isPresent()) {
            return userDao.deleteUserByUserUid(userUid);
        }
        return -1;
    }

    public int updateUser(User user) {
        Optional<User> optionalUser = getUser(user.getUserUid());
        if (optionalUser.isPresent()) {
            return userDao.updateUser(user);
        }
        return -1;
    }
}
