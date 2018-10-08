package com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.dao;

import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.model.User;
import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.model.User.Gender;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FakeDataDao implements UserDao {

    private Map<UUID, User> database;

    public FakeDataDao() {
        database = new HashMap<>();
        UUID johnUid = UUID.randomUUID();
        database.put(johnUid, new User(
                johnUid, "John", "Jones", Gender.MALE, 23, "john.jones@spring.org"));
    }

    @Override
    public List<User> selectAllUsers() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<User> selectUserByUserUid(UUID userUid) {
        return Optional.ofNullable(database.get(userUid));
    }

    @Override
    public int insertUser(UUID userUid, User user) {
        database.put(userUid, user);
        return 1;
    }

    @Override
    public int deleteUserByUserUid(UUID userUid) {
        database.remove(userUid);
        return 1;
    }

    @Override
    public int updateUser(User user) {
        database.put(user.getUserUid(), user);
        return 1;
    }
}
