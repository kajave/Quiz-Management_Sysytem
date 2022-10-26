package com.epam.quiz.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import com.epam.quiz.model.QuestionDTO;
import com.epam.quiz.model.QuizDTO;
import com.epam.quiz.model.UserDTO;

@Controller
@RequestMapping("/user")
@SessionAttributes("username")
public class UserController {
	@Autowired
	WebClient.Builder builder;

	int questionCount = 0;
	int mark = 0;
	QuizDTO quiz;

	@GetMapping
	public ModelAndView quizDashboard(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		List<String> quizs = builder.build().get().uri("http://localhost:9090/quiz")
				.headers(headers -> headers.setBearerAuth((String) session.getAttribute("jwt"))).retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<String>>() {
				}).block();
		modelAndView.addObject("username", "Aniket");
		modelAndView.addObject("quizName", quizs);
		modelAndView.setViewName("Test");
		return modelAndView;
	}

	@PostMapping(value = "/uservalidate")
	public ModelAndView validateUser(@Valid UserDTO userDTO, BindingResult result, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if (result.hasErrors()) {
			modelAndView.setViewName("redirect:/login/admin");
		} else {

			String jwt = builder.build().post().uri("http://localhost:9090/authenticate").bodyValue(userDTO)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).retrieve()
					.bodyToMono(String.class).block();

			modelAndView.addObject("username", userDTO.getUsername());
			session.setAttribute("jwt", jwt);

			List<String> quizName = builder.build().get().uri("http://localhost:9090/quiz")
					.headers(headers -> headers.setBearerAuth((String) session.getAttribute("jwt"))).retrieve()
					.bodyToMono(new ParameterizedTypeReference<List<String>>() {
					}).block();
			modelAndView.addObject("quizName", quizName);
			modelAndView.setViewName("Test");
		}

		return modelAndView;
	}

	@GetMapping(value = "/quiz")
	public ModelAndView loadQuiz(@RequestParam("quizName") String name, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		quiz = builder.build().get().uri("http://localhost:9090/quiz/" + name)
				.headers(headers -> headers.setBearerAuth(session.getAttribute("jwt").toString())).retrieve()
				.bodyToMono(QuizDTO.class).block();
		
		modelAndView.setViewName("redirect:/user/loadquiz");
		return modelAndView;
	}

	@GetMapping(value = "/loadquiz")
	public ModelAndView show() {
		ModelAndView modelAndView = new ModelAndView();

		if (questionCount < quiz.getList().size()) {
			modelAndView.addObject("name", quiz.getQuizName());
			modelAndView.addObject("question", quiz.getList().get(questionCount));
			modelAndView.setViewName("showTest2");
		} else {
			modelAndView.addObject("quizname", quiz.getQuizName());
			modelAndView.addObject("quizmark", mark);
			modelAndView.setViewName("Result");
			questionCount = 0;
			mark = 0;
		}
		return modelAndView;
	}

	@PostMapping("/validate")
	public ModelAndView validateAnswer(QuestionDTO question, @RequestParam(defaultValue = "no") String choice) {
		if (choice.equals(question.getAns())) {
			mark = mark + question.getMark();
		}
		questionCount++;
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:/user/loadquiz");
		return view;
	}

	@GetMapping(value = "/logout")
	public ModelAndView logout(SessionStatus sessionStatus, HttpSession session) {
		sessionStatus.setComplete();
		session.invalidate();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Home");
		return modelAndView;
	}


}
