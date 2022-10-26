package com.epam.quiz.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

public class QuizDTO {
	private int id;
	@NotBlank(message = "Quiz name must be present")
	private String quizName;
	
	private List<QuestionDTO> list;
	
	public QuizDTO() {
		
	}
	public QuizDTO(String quizName, List<QuestionDTO> list) {
		
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
	public List<QuestionDTO> getList() {
		return list;
	}
	public void setList(List<QuestionDTO> list) {
		this.list = list;
	}
	
	

}
