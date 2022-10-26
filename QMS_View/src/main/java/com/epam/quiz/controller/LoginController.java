package com.epam.quiz.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import com.epam.quiz.model.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/login")
@SessionAttributes("username")
public class LoginController {

	@Autowired
	WebClient.Builder builder;

	@GetMapping
	public ModelAndView loginMenu() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Home");
		return modelAndView;
	}

	@GetMapping(value = "/user")
	public ModelAndView userDashboard(@ModelAttribute("user") UserDTO user) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("UserLogin");
		modelAndView.addObject("url", "user/uservalidate");
		modelAndView.addObject("input", "User");
		return modelAndView;
	}

	@GetMapping(value = "/admin")
	public ModelAndView adminDashboard(@ModelAttribute("user") UserDTO admin) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("url", "login/adminvalidate");
		modelAndView.addObject("input", "Admin");
		modelAndView.setViewName("UserLogin");
		return modelAndView;
	}

	@PostMapping(value = "/adminvalidate")
	public ModelAndView validateAdmin(@Valid UserDTO adminDTO, BindingResult result,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if (result.hasErrors()) {
			modelAndView.setViewName("redirect:/login/admin");

		} else {
			String jwt=builder.build().post().uri("http://localhost:9090/authenticate").bodyValue(adminDTO)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).retrieve()
					.bodyToMono(String.class).block();
			modelAndView.addObject("username", adminDTO.getUsername());
			modelAndView.setViewName("Menu");
			session.setAttribute("jwt", jwt);
		}

		return modelAndView;
	}

	@PostMapping(value = "/register")
	public ModelAndView register(UserDTO userDTO) throws JsonProcessingException {
		ModelAndView modelAndView = new ModelAndView();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValueAsString(userDTO);
		builder.build().post().uri("http://localhost:9090/login/user/register")
				.bodyValue(userDTO)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).retrieve().bodyToMono(String.class)
				.block();
		modelAndView.setViewName("UserLogin");
		return modelAndView;
	}

	@GetMapping(value = "/register")
	public ModelAndView registerView() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Register");
		return modelAndView;
	}

	@GetMapping(value = "/logout")
	public ModelAndView logout(SessionStatus sessionStatus,HttpSession session) {
		sessionStatus.setComplete();
		session.invalidate();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Home");
		return modelAndView;
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleException(Exception exception) {
		ModelAndView modelAndView = new ModelAndView("Error");
		modelAndView.addObject("message", exception.getMessage());
		return modelAndView;
	}

}
