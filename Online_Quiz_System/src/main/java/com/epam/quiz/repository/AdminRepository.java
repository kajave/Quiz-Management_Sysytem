package com.epam.quiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epam.quiz.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, String>{
	
	@Query("SELECT u FROM Admin u WHERE u.username=?1 and u.password=?2")
	public Optional<Admin> findByAdminNameAndPassword(String username,String password);

}
