package com.epam.quiz.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.quiz.exception.UserException;
import com.epam.quiz.model.Admin;
import com.epam.quiz.model.User;
import com.epam.quiz.repository.AdminRepository;
import com.epam.quiz.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginServiceTest {

	@Mock
	UserRepository userRepository;

	@Mock
	AdminRepository adminRepository;

	@InjectMocks
	LoginService loginService;

	@Test
	void test_validateUserDetails() throws UserException {
		Optional<User> user = Optional.ofNullable(new User());
		when(userRepository.findByUserNameAndPassword("admin", "123")).thenReturn(user);
		assertEquals(user.get(), loginService.validateUserDetails(new User("admin", "123")));

		when(userRepository.findByUserNameAndPassword("admin", "123")).thenReturn(user.empty());
		assertThrows(UserException.class, () -> loginService.validateUserDetails(new User("admin", "123")));
	}

	@Test
	void test_validateUserDetails_getException() throws UserException {
		when(userRepository.findByUserNameAndPassword("admin", "123")).thenThrow(NoSuchElementException.class);
		assertThrows(UserException.class, () -> loginService.validateUserDetails(new User("admin", "123")));
	}

	@Test
	void test_validateAdminDetails() throws UserException {
		Optional<Admin> admin = Optional.ofNullable(new Admin());
		when(adminRepository.findByAdminNameAndPassword("admin", "123")).thenReturn(admin);
		assertEquals(admin.get(), loginService.validateAdminDetails(new Admin("admin", "123")));
		
		when(adminRepository.findByAdminNameAndPassword("admin", "123")).thenReturn(admin.empty());
		assertThrows(UserException.class, () -> loginService.validateAdminDetails(new Admin("admin", "123")));

	}

	@Test
	void test_validateAdminDetails_getException() throws UserException {
		when(adminRepository.findByAdminNameAndPassword("admin", "123")).thenThrow(NoSuchElementException.class);
		assertThrows(UserException.class, () -> loginService.validateAdminDetails(new Admin("admin", "123")));
	}

	@Test
	void test_addUser_succesfully() throws UserException {
		User user = new User("Aniket", "password");
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, loginService.add(user));
		assertEquals("Aniket", loginService.add(user).getUsername());
	}

	@Test
	void test_addUser_fail() throws UserException {
		User user = new User("Aniket", "password");
		when(userRepository.save(user)).thenThrow(IllegalStateException.class);
		assertThrows(UserException.class, () -> loginService.add(user));
		
		when(userRepository.save(any(User.class))).thenReturn(null);
		assertThrows(UserException.class, () -> loginService.add(user));

	}
	
	
}
