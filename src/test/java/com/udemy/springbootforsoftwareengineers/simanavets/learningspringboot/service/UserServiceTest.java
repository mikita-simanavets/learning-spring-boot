package com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.service;

import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.dao.FakeDataDao;
import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    @Mock
    private FakeDataDao fakeDataDao;

    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeDataDao);
    }

    @Test
    public void shouldGetAllUsers() {
        UUID annaUserUid = UUID.randomUUID();
        User anna = new User(annaUserUid,
                "Anna", "Montana", User.Gender.FEMALE, 20, "anna.montana@spring.org");

        ArrayList<User> users = new ArrayList<>();
        users.add(anna);

        given(fakeDataDao.selectAllUsers()).willReturn(users);

        List<User> allUsers = userService.getAllUsers();

        assertThat(allUsers).hasSize(1);

        User user = allUsers.get(0);
        assertUserFields(user);
    }

    @Test
    public void shouldGetUser() {
        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid,
                "Anna", "Montana", User.Gender.FEMALE, 20, "anna.montana@spring.org");
        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(anna));

        Optional<User> optionalUser = fakeDataDao.selectUserByUserUid(annaUid);

        assertThat(optionalUser).isPresent();

        User user = optionalUser.get();
        assertUserFields(user);
    }

    @Test
    public void shouldInsertUser() {
        User anna = new User(null,
                "Anna", "Montana", User.Gender.FEMALE, 20, "anna.montana@spring.org");
        given(fakeDataDao.insertUser(any(UUID.class), eq(anna))).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int insertResult = userService.insertUser(anna);

        verify(fakeDataDao).insertUser(any(UUID.class), captor.capture());

        User user = captor.getValue();
        assertUserFields(user);

        assertThat(insertResult).isEqualTo(1);
    }

    @Test
    public void shouldRemoveUser() {
        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid,
                "Anna", "Montana", User.Gender.FEMALE, 20, "anna.montana@spring.org");
        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(anna));
        given(fakeDataDao.deleteUserByUserUid(annaUid)).willReturn(1);

        int deleteResult = userService.removeUser(annaUid);

        verify(fakeDataDao).selectUserByUserUid(annaUid);
        verify(fakeDataDao).deleteUserByUserUid(annaUid);

        assertThat(deleteResult).isEqualTo(1);
    }

    @Test
    public void shouldUpdateUser() {
        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid,
                "Anna", "Montana", User.Gender.FEMALE, 20, "anna.montana@spring.org");
        given(fakeDataDao.selectUserByUserUid(annaUid)).willReturn(Optional.of(anna));
        given(fakeDataDao.updateUser(anna)).willReturn(1);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        int updateResult = userService.updateUser(anna);

        verify(fakeDataDao).selectUserByUserUid(annaUid);
        verify(fakeDataDao).updateUser(captor.capture());

        User user = captor.getValue();
        assertUserFields(user);

        assertThat(updateResult).isEqualTo(1);
    }

    private void assertUserFields(User user) {
        assertThat(user.getAge()).isEqualTo(20);
        assertThat(user.getFirstName()).isEqualTo("Anna");
        assertThat(user.getLastName()).isEqualTo("Montana");
        assertThat(user.getGender()).isEqualTo(User.Gender.FEMALE);
        assertThat(user.getEmail()).isEqualTo("anna.montana@spring.org");
        assertThat(user.getUserUid())
                .isNotNull()
                .isInstanceOf(UUID.class);
    }
}