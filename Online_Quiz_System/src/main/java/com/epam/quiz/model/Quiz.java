package com.epam.quiz.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String quizName;
	
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL,fetch = FetchType.EAGER)	
	private List<Question> list;
	
	public Quiz() {
		
	}
	public Quiz(String quizName, List<Question> list) {
		
		this.quizName = quizName;
		this.list = list;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuizName() {
		return quizName;
	}
	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}
	public List<Question> getList() {
		return list;
	}
	public void setList(List<Question> list) {
		this.list = list;
	}
	
	

}
