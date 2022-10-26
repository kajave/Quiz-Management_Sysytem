package com.epam.quiz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.epam.quiz.exception.UserException;
import com.epam.quiz.model.Admin;
import com.epam.quiz.model.User;
import com.epam.quiz.repository.AdminRepository;
import com.epam.quiz.repository.UserRepository;

@Service
public class LoginService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	AdminRepository adminRepository;

	
	public User validateUserDetails(User userDetails) throws UserException {
		Optional<User> user=Optional.empty();
		try {
			user =userRepository.findByUserNameAndPassword(userDetails.getUsername(), userDetails.getPassword());
		}catch (RuntimeException e) {
			throw new UserException("User not found");
		}
		return user.orElseThrow(()->new UserException("User not found"));

	}

	public User add(User user) throws UserException {
		Optional<User> userOptional=Optional.empty();
		try {
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			userOptional =Optional.of(userRepository.save(user));
		}catch (RuntimeException e) {
			throw new UserException("User is not Addeded suceesfully");
		}
		return userOptional.orElseThrow(()->new UserException("User is not Addeded suceesfully"));
		
	}

	public Admin validateAdminDetails(Admin admin) throws UserException {
		Optional<Admin> admin1;
		try {
			admin1 =adminRepository.findByAdminNameAndPassword(admin.getUsername(), admin.getPassword());
		}catch (RuntimeException e) {
			throw new UserException("User not found");
		}
		return admin1.orElseThrow(()->new UserException("User not found"));
	}
	


}
