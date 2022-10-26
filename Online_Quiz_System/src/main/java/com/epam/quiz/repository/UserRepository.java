package com.epam.quiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epam.quiz.model.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	@Query("SELECT u FROM User u WHERE u.username=?1 and u.password=?2")
	public Optional<User> findByUserNameAndPassword(String username,String password);
	
	

}
