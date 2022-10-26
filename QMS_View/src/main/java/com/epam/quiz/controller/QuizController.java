package com.epam.quiz.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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
@RequestMapping(value = "/quiz")
public class QuizController {

	@Autowired
	WebClient.Builder builder;

	@GetMapping
	public ModelAndView menu() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Menu");
		return modelAndView;
	}

	@GetMapping(value = "/show")
	public ModelAndView showQuizName(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		List<String> quizName = builder.build().get().uri("http://localhost:9090/quiz")
				.headers(headers -> headers.setBearerAuth((String) session.getAttribute("jwt"))).retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<String>>() {
				}).block();
		modelAndView.addObject("quizName", quizName);
		modelAndView.setViewName("showQuizName");
		return modelAndView;
	}

	@GetMapping(value = "/loadquiz")
	public ModelAndView displayQuiz(@RequestParam("quizName") String name, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		QuizDTO quiz = builder.build().get().uri("http://localhost:9090/quiz/" + name)
				.headers(headers -> headers.setBearerAuth((String) session.getAttribute("jwt"))).retrieve()
				.bodyToMono(QuizDTO.class).block();
		modelAndView.addObject("quiz", quiz);
		modelAndView.setViewName("showQuiz");
		return modelAndView;
	}

	@GetMapping(value = "/edit")
	public ModelAndView editQuiz(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		List<String> quizName = builder.build().get().uri("http://localhost:9090/quiz")
				.headers(headers -> headers.setBearerAuth((String) session.getAttribute("jwt"))).retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<String>>() {
				}).block();
		modelAndView.addObject("quizName", quizName);
		modelAndView.setViewName("QuizEdit");
		return modelAndView;
	}

	@PostMapping(value = "/changename")
	public ModelAndView changeName(@RequestParam Map<String, String> map, HttpSession session) {
		ModelAndView modelAndView;
		String oldName = map.get("oldname");
		String newName = map.get("newname");
		builder.build().put().uri("http://localhost:9090/quiz/" + oldName + "/" + newName)
				.headers(headers -> headers.setBearerAuth((String) session.getAttribute("jwt"))).retrieve()
				.bodyToMono(String.class).block();
		modelAndView = editQuiz(session);
		return modelAndView;
	}

	@GetMapping(value = "/changename")
	public ModelAndView changeNameDashboard(@RequestParam("quizName") String name) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("name", name);
		modelAndView.setViewName("changeName");
		return modelAndView;
	}

	@RequestMapping(value = "/deletequiz")
	public ModelAndView delete(@RequestParam("quizName") String name, HttpSession session) {
		ModelAndView modelAndView;
		builder.build().delete().uri("http://localhost:9090/quiz/" + name)
				.headers(headers -> headers.setBearerAuth((String) session.getAttribute("jwt"))).retrieve()
				.bodyToMono(String.class).block();
		modelAndView = editQuiz(session);
		return modelAndView;
	}

	@GetMapping(value = "add")
	public ModelAndView addQuiz() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("QuizTemplate");
		modelAndView.addObject("input", "first");
		return modelAndView;
	}

	@RequestMapping(value = "addquiz")
	public ModelAndView quizTemplate(@ModelAttribute("obj") QuestionDTO question, HttpServletRequest request,
			HttpSession session){
		String qname = request.getParameter("name");
		ModelAndView modelAndView = new ModelAndView();
		QuizDTO quiz = new QuizDTO();
		quiz.setQuizName(qname);
		List<String> quizName = builder.build().get().uri("http://localhost:9090/quiz")
				.headers(headers -> headers.setBearerAuth((String) session.getAttribute("jwt"))).retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<String>>() {
				}).block();
		if (!quizName.contains(qname)) {
			builder.build().post().uri("http://localhost:9090/quiz/").bodyValue(quiz)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					.headers(headers -> headers.setBearerAuth((String) session.getAttribute("jwt"))).retrieve()
					.bodyToMono(QuizDTO.class).block();
		}

		modelAndView.addObject("quizname", qname);
		modelAndView.setViewName("Add");
		modelAndView.addObject("url", "/quiz/addquestion");
		return modelAndView;
	}

	@PostMapping(value = "addquestion")
	public ModelAndView addQuestion(QuestionDTO questionDTO, @RequestParam Map<String, String> map, HttpSession session){
		//QuestionDTO question = questionService.convertDTOQuestion(questionDTO, map);

		builder.build().post().uri("http://localhost:9090/quiz/question/" + map.get("quizname")).bodyValue(questionDTO)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.headers(headers -> headers.setBearerAuth((String) session.getAttribute("jwt"))).retrieve()
				.bodyToMono(String.class).block();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("QuizTemplate");
		modelAndView.addObject("input", "secound");
		modelAndView.addObject("name", map.get("quizname"));
		return modelAndView;
	}


}
