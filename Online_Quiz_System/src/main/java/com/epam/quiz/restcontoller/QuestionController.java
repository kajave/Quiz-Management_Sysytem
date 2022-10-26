package com.epam.quiz.restcontoller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.quiz.exception.QuestionException;
import com.epam.quiz.exception.QuizException;
import com.epam.quiz.model.QuestionDTO;
import com.epam.quiz.service.QuestionService;
import com.epam.quiz.service.QuizService;

@RestController
@RequestMapping(value = "quiz/question")
public class QuestionController {

	@Autowired
	QuestionService questionService;
	@Autowired
	QuizService quizService;
	@Autowired
	ModelMapper mapper;

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<QuestionDTO> showQuestion() throws QuestionException {
		return questionService.get();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public QuestionDTO getQuestionByID(@PathVariable int id) throws QuestionException {
		return questionService.getById(id);
	}

	@DeleteMapping(value = "/{questionname}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String delete(@PathVariable("questionname") String name) throws QuestionException {
		questionService.delete(name);
		return "Question deleted succesfully";
	}

	@PostMapping(value = "/{quizname}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String add(@RequestBody QuestionDTO questionDTO, @PathVariable("quizname") String name)
			throws QuizException, QuestionException {
		questionDTO.setQuizname(name);
		questionService.add(questionDTO);
		return "Question added succesfully";
	}

	@PutMapping(value = "/{quizname}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String update(@RequestBody QuestionDTO questionDTO, @PathVariable String quizname)
			throws QuestionException, QuizException {
		questionDTO.setQuizname(quizname);
		questionService.add(questionDTO);
		return "Question update succesfully";
	}

}
