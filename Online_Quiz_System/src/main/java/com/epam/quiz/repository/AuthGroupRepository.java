package com.epam.quiz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.quiz.model.AuthGroup;

public interface AuthGroupRepository extends JpaRepository<AuthGroup, Long> {
	
    List<AuthGroup> findByUsername(String username);
}