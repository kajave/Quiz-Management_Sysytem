package com.epam.quiz.exception;

public class UserException extends Exception {
	private String name;
	public String getName() {
		return name;
	}

	public UserException(String name) {
		this.name=name;
	}
	
	

}
