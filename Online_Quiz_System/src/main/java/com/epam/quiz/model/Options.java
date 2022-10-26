package com.epam.quiz.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Options {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
