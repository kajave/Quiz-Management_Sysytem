package com.epam.quiz.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.epam.quiz.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer>{
	

	@Modifying
	@Query("DELETE FROM Question q WHERE q.name=?1")
	public int deleteByName(String name);
	

}
