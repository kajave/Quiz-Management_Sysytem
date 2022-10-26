package com.epam.quiz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.epam.quiz.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

	@Modifying
	@Query("DELETE FROM Quiz q WHERE q.quizName=?1")
	public int deleteByQuizName(String quizName);

	public Optional<Quiz> findByQuizName(String quizName);

	@Query("SELECT q.quizName FROM Quiz q")
	public List<String> getQuizName();
}
