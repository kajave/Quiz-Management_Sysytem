package com.epam.quiz.restcontoller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.quiz.exception.UserException;
import com.epam.quiz.model.User;
import com.epam.quiz.model.UserDTO;
import com.epam.quiz.service.LoginService;

@RequestMapping(value = "/login")
@RestController
public class LoginController {
	@Autowired
	LoginService loginService;

	@Autowired
	ModelMapper mapper;
	
	@PostMapping(value = "/user/register")
	public String register(@RequestBody UserDTO userDTO) throws UserException {
		User user = mapper.map(userDTO, User.class);
		loginService.add(user);
		return "User register succesfully";
	}
	

}
