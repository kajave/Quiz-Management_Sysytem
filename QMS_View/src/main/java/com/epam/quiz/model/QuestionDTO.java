package com.epam.quiz.model;

import java.util.List;

public class QuestionDTO {
	
	private int id;
	
	private String name;
	
	List<OptionsDTO> list;
	
	private String ans;

	private int mark;
	
	QuizDTO quiz;
	

	private String quizname;
	
	
	public String getQuizname() {
		return quizname;
	}

	public void setQuizname(String quizname) {
		this.quizname = quizname;
	}

	
	

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

	public List<OptionsDTO> getList() {
		return list;
	}

	public void setList(List<OptionsDTO> list) {
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
