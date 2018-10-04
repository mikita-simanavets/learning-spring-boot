package com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.dao;

import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.model.User;
import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.model.User.Gender;

import java.util.*;

public class FakeDataDao implements UserDao {

    private static Map<UUID, User> database;

    static {
        database = new HashMap<>();
        UUID johnUid = UUID.randomUUID();
        database.put(johnUid, new User(
                johnUid, "John", "Jones", Gender.MALE, 23, "john.jones@spring.org"));
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(database.values());
    }

    @Override
    public User getUser(UUID userUid) {
        return database.get(userUid);
    }

    @Override
    public int insertUser(UUID userUid, User user) {
        database.put(userUid, user);
        return 1;
    }

    @Override
    public int removeUser(UUID userUid) {
        database.remove(userUid);
        return 1;
    }

    @Override
    public int updateUser(User user) {
        database.put(user.getUserUid(), user);
        return 1;
    }
}
