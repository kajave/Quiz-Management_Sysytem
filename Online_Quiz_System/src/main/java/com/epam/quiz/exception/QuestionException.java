package com.epam.quiz.exception;

import org.springframework.stereotype.Component;

@Component
public class QuestionException extends Exception{
	final  String name;
	public QuestionException() {
		this.name = "";
		
	}
	public QuestionException(String name) {
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	
	
	

}
