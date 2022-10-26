package com.epam.quiz.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.epam.quiz.model.AuthGroup;
import com.epam.quiz.model.User;
import com.epam.quiz.repository.AuthGroupRepository;
import com.epam.quiz.repository.UserRepository;

@Component
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository repository;

	@Autowired
	AuthGroupRepository authGroupRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> optional = repository.findById(username);
		User user = optional.orElseThrow(() -> new UsernameNotFoundException("cannot find user"));

		List<AuthGroup> roles = authGroupRepository.findByUsername(username);
		org.springframework.security.core.userdetails.User userDetail = new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), roles.stream()
						.map(role -> new SimpleGrantedAuthority(role.getAuthGroup())).collect(Collectors.toList()));
		return userDetail;
	}

}
