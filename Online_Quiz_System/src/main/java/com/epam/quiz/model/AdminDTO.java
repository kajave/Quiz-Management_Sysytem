package com.epam.quiz.model;

import javax.validation.constraints.NotBlank;


public class AdminDTO {

	@NotBlank(message = "username must required")
	private String username;
	
	@NotBlank(message = "password must required")
	private String password;
	
	public AdminDTO() {
	}
	
	public AdminDTO(String username, String password) {
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
