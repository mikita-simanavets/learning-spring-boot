package com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.integration;

import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.clientproxy.UserResourceV1;
import com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

import static com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.model.User.Gender.FEMALE;
import static com.udemy.springbootforsoftwareengineers.simanavets.learningspringboot.model.User.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class UserIntegrationTests {

	@Autowired
	private UserResourceV1 userResourceV1;

	@Test
	public void shouldInsertUser() {
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "John", "Jones",
				User.Gender.MALE, 23, "john.jones@spring.org");

		userResourceV1.insertNewUser(user);

		User john = userResourceV1.fetchUser(userUid);
		assertThat(john).isEqualToComparingFieldByField(user);
	}

	@Test
	public void shouldDeleteUser() {
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "John", "Jones",
				User.Gender.MALE, 23, "john.jones@spring.org");

		userResourceV1.insertNewUser(user);

		User john = userResourceV1.fetchUser(userUid);
		assertThat(john).isEqualToComparingFieldByField(user);

		userResourceV1.deleteUser(userUid);

		assertThatThrownBy(() -> userResourceV1.fetchUser(userUid))
				.isInstanceOf(NotFoundException.class);
	}

	@Test
	public void shouldUpdateUser() {
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "John", "Jones",
				User.Gender.MALE, 23, "john.jones@spring.org");

		userResourceV1.insertNewUser(user);

		User updatedUser = new User(userUid, "Alex", "Jones",
				User.Gender.MALE, 33, "alex.jones@spring.org");

		userResourceV1.updateUser(updatedUser);

		user = userResourceV1.fetchUser(userUid);
		assertThat(user).isEqualToComparingFieldByField(updatedUser);
	}

	@Test
	public void shouldFetchUsersByGender() {
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "John", "Jones",
				User.Gender.MALE, 23, "john.jones@spring.org");

		userResourceV1.insertNewUser(user);

		List<User> females = userResourceV1.fetchUsers(FEMALE.name());

		assertThat(females).extracting("gender").doesNotContain(MALE.name());
		assertThat(females).extracting("userUid").doesNotContain(userUid);

		List<User> males = userResourceV1.fetchUsers(MALE.name());

		assertThat(males).extracting("gender").doesNotContain(FEMALE.name());
		assertThat(males).extracting("userUid").contains(userUid);
	}
}
