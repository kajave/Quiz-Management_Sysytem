package com.epam.quiz.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin {
	@Id
	private String username;
	
	private String password;
	
	public Admin() {
	}
	
	public Admin(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}


	public void setUsername(String userName) {
		this.username = userName;
	}
	public String getUsername() {
		return username;
	}
	public void setPassword(String passward) {
		this.password = passward;
	}
	public String getPassword() {
		return password;
	}
	
	
	
	

}
