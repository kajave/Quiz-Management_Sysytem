package com.epam.quiz.exception;

public class QuizException extends Exception {
	final  String name;
	
	public QuizException(String name) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}

}
