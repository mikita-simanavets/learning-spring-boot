package com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.dao;

import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.model.User;
import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.model.User.Gender;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class FakeDataDaoTest {

    private FakeDataDao fakeDataDao;

    @Before
    public void setUp() {
        fakeDataDao = new FakeDataDao();
    }

    @Test
    public void shouldSelectAllUsers() {
        List<User> users = fakeDataDao.selectAllUsers();
        assertThat(users).hasSize(1);

        User user = users.get(0);

        assertThat(user.getAge()).isEqualTo(23);
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Jones");
        assertThat(user.getGender()).isEqualTo(Gender.MALE);
        assertThat(user.getEmail()).isEqualTo("john.jones@spring.org");
        assertThat(user.getUserUid()).isNotNull();
    }

    @Test
    public void shouldSelectUserByUserUid() {
        UUID annaUserUid = UUID.randomUUID();
        User anna = new User(annaUserUid,
                "Anna", "Montana", Gender.FEMALE, 20, "anna.montana@spring.org");
        fakeDataDao.insertUser(annaUserUid, anna);
        assertThat(fakeDataDao.selectAllUsers()).hasSize(2);

        Optional<User> annaOptional = fakeDataDao.selectUserByUserUid(annaUserUid);
        assertThat(annaOptional).isPresent();
        assertThat(annaOptional.get()).isEqualToComparingFieldByField(anna);
    }

    @Test
    public void shouldNotSelectUserByRandomUserUid() {
        Optional<User> user = fakeDataDao.selectUserByUserUid(UUID.randomUUID());
        assertThat(user).isNotPresent();
    }

    @Test
    public void shouldInsertUser() {
        UUID annaUserUid = UUID.randomUUID();
        User anna = new User(annaUserUid,
                "Anna", "Montana", Gender.FEMALE, 20, "anna.montana@spring.org");
        fakeDataDao.insertUser(annaUserUid, anna);

        assertThat(fakeDataDao.selectAllUsers()).hasSize(2);

        Optional<User> annaOptional = fakeDataDao.selectUserByUserUid(annaUserUid);
        assertThat(annaOptional).isPresent();
        assertThat(annaOptional.get()).isEqualToComparingFieldByField(anna);
    }

    @Test
    public void shouldDeleteUserByUserUid() {
        UUID johnUserUid = fakeDataDao.selectAllUsers().get(0).getUserUid();

        fakeDataDao.deleteUserByUserUid(johnUserUid);

        assertThat(fakeDataDao.selectUserByUserUid(johnUserUid)).isNotPresent();
        assertThat(fakeDataDao.selectAllUsers()).isEmpty();
    }

    @Test
    public void shouldUpdateUser() {
        UUID johnUserUid = fakeDataDao.selectAllUsers().get(0).getUserUid();
        User newJohn = new User(johnUserUid,
                "Anna", "Montana", Gender.FEMALE, 20, "anna.montana@spring.org");
        fakeDataDao.updateUser(newJohn);

        Optional<User> user = fakeDataDao.selectUserByUserUid(johnUserUid);
        assertThat(user).isPresent();

        assertThat(fakeDataDao.selectAllUsers()).hasSize(1);
        assertThat(user.get()).isEqualToComparingFieldByField(newJohn);
    }
}
