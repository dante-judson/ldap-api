package com.desafio.atlantico;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.desafio.atlantico.business.UserBusiness;
import com.desafio.atlantico.business.exception.ResourceNotFoundException;
import com.desafio.atlantico.entity.User;
import com.desafio.atlantico.repository.UserLdapRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserBusinessTest {

	@InjectMocks
	private UserBusiness userBusiness;

	@Mock
	private UserLdapRepository mockedRepo;

	@Test
	void findAllTest() {
		when(mockedRepo.findAll()).thenReturn(new ArrayList<User>());

		Iterable<User> users = userBusiness.findAll();
		assertEquals(new ArrayList<User>(), users);
	}

	@Test
	void createUserTest() {
		User user = createMockUser();

		when(mockedRepo.save(any(User.class))).thenReturn(user);

		User savedUser = userBusiness.createUser(user);

		assertEquals(user, savedUser);
	}

	@Test
	void createUserNameAlreadBoundExceptionTest() {
		User user = createMockUser();

		when(mockedRepo.save(any(User.class))).thenThrow(NameAlreadyBoundException.class);

		try {
			userBusiness.createUser(user);
			fail("should not reach this code!");
		} catch (NameAlreadyBoundException e) {
			assertThat(true).isTrue();
		}
	}

	@Test
	void findByUidTest() {
		User user = createMockUser();

		when(mockedRepo.findByUid(user.getUid())).thenReturn(Optional.ofNullable(user));

		try {
			User findedUser = userBusiness.findByUid(user.getUid());
			assertEquals(user, findedUser);
		} catch (ResourceNotFoundException e) {
			fail("should not throw an exception");
		}

	}

	@Test
	void findByUidResourceNotFoundExceptionTest() {
		User user = createMockUser();

		when(mockedRepo.findByUid(user.getUid())).thenReturn(Optional.ofNullable(null));

		try {
			userBusiness.findByUid(user.getUid());
			fail("should not reach this code!");
		} catch (ResourceNotFoundException e) {
			assertThat(true).isTrue();
		}

	}

	@Test
	void deleteTest() {
		User user = createMockUser();

		when(mockedRepo.findByUid(user.getUid())).thenReturn(Optional.ofNullable(user));

		try {
			userBusiness.deleteUser(user.getUid());
			assertThat(true).isTrue();
		} catch (ResourceNotFoundException e) {
			fail("should not throw an exception");
		}

	}

	@Test
	void deleteResourceNotFoundExceptionTest() {
		User user = createMockUser();

		when(mockedRepo.findByUid(user.getUid())).thenReturn(Optional.ofNullable(null));

		try {
			userBusiness.deleteUser(user.getUid());
			fail("should not reach this code!");
		} catch (ResourceNotFoundException e) {
			assertThat(true).isTrue();
		}

	}

	private User createMockUser() {
		User user = new User();

		user.setCn("anystring");
		user.setUid("anystring");
		user.setSn("anystring");
		return user;
	}

}
