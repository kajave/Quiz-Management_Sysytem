package com.epam.quiz.model;

public class OptionsDTO {
	
	private int id;
	private String choice;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getChoice() {
		return choice;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	@Override
	public String toString() {
		return choice;
	}
	
	
	

}
