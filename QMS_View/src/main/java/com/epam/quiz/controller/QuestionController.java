package com.epam.quiz.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import com.epam.quiz.model.QuestionDTO;
import com.epam.quiz.model.QuizDTO;

@Controller
@RequestMapping(value = "/question")
public class QuestionController {


	@Autowired
	QuizController quizController;

	@Autowired
	WebClient.Builder builder;

	@GetMapping(value = "/updatequestion")
	public ModelAndView questionEditDashboard(@RequestParam("quizName") String name, HttpSession session){
		ModelAndView modelAndView = new ModelAndView();
		QuizDTO quiz = builder.build().get().uri("http://localhost:9090/quiz/" + name)
				.headers(headers -> headers.setBearerAuth((String) session.getAttribute("jwt"))).retrieve()
				.bodyToMono(QuizDTO.class).block();
		modelAndView.addObject("quiz", quiz);
		modelAndView.setViewName("QuestionEdit");
		return modelAndView;
	}

	@GetMapping(value = "/deleteQuestion")
	public ModelAndView deleteQuestion(@RequestParam("name") String name, HttpSession session) {
		ModelAndView modelAndView;
		builder.build().delete().uri("http://localhost:9090/quiz/question" + name)
				.headers(headers -> headers.setBearerAuth((String) session.getAttribute("jwt"))).retrieve()
				.bodyToMono(String.class).block();
		modelAndView = quizController.editQuiz(session);
		return modelAndView;
	}

	@GetMapping(value = "add")
	public ModelAndView add(@ModelAttribute("obj") QuestionDTO questionDTO, @RequestParam("quizName") String name) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("quizname", name);
		modelAndView.setViewName("Add");
		modelAndView.addObject("url", "/question/addquestion");
		return modelAndView;
	}

	@PostMapping(value = "addquestion")
	public ModelAndView addQuestion(QuestionDTO questionDTO, @RequestParam Map<String, String> map,HttpSession session){
		//QuestionDTO question = questionService.convertDTOQuestion(questionDTO, map);
		builder.build().post().uri("http://localhost:9090/quiz/question/" + map.get("quizname")).bodyValue(questionDTO)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.headers(headers->headers.setBearerAuth((String) session.getAttribute("jwt"))).retrieve()
				.bodyToMono(String.class)
				.block();

		return quizController.showQuizName(session);
	}

}
