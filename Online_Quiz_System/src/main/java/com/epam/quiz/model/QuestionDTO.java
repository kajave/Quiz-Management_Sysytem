package com.epam.quiz.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class QuestionDTO {
	
	private int id;
	
	private String name;
	
	List<Options> list;
	
	private String ans;

	private int mark;
	
	@JsonIgnore
	private String quizname;
	
	
	public String getQuizname() {
		return quizname;
	}

	public void setQuizname(String quizname) {
		this.quizname = quizname;
	}




	@JsonIgnore
	QuizDTO quiz;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Options> getList() {
		return list;
	}

	public void setList(List<Options> list) {
		this.list = list;
	}

	public String getAns() {
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public QuizDTO getQuiz() {
		return quiz;
	}

	public void setQuiz(QuizDTO quiz) {
		this.quiz = quiz;
	}

	
	

	@Override
	public String toString() {
		return "Question [name=" + name + ", list=" + list + ", ans=" + ans + ", mark=" + mark + "]";
	}
	
	
	

}
