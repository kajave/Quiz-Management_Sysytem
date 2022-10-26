package com.epam.quiz.model;

import javax.validation.constraints.NotBlank;

public class UserDTO {

    @NotBlank(message = "username must required")
	private String username;

    @NotBlank(message = "password must required")
	private String password;

	public UserDTO() {

	}

	public UserDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
