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

import com.epam.quiz.exception.QuizException;
import com.epam.quiz.model.QuizDTO;
import com.epam.quiz.service.QuestionService;
import com.epam.quiz.service.QuizService;

@RestController
@RequestMapping(value = "/quiz")
public class QuizController {

	@Autowired
	QuizService quizService;
	@Autowired
	QuestionService questionService;
	@Autowired
	ModelMapper mapper;

	@GetMapping
	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	public List<String> showQuizName() throws QuizException {
		return quizService.getQuizNames();

	}

	@GetMapping(value = "/{quizname}")
	@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
	public QuizDTO displayQuiz(@PathVariable("quizname") String name) throws QuizException {
		return quizService.getQuiz(name);
	}

	@PutMapping(value = "{oldname}/{newname}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String changeName(@PathVariable("oldname") String oldname, @PathVariable("newname") String newname)
			throws QuizException {
		quizService.changeQuizName(oldname, newname);
		return "Quiz name updated succesfully";
	}

	@DeleteMapping(value = "/{quizname}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String delete(@PathVariable("quizname") String name) throws QuizException {
		quizService.deleteQuize(name);
		return "Quiz Delete succesfully";
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public QuizDTO add(@RequestBody QuizDTO quizDTO) throws QuizException {
		return quizService.add(quizDTO);

	}

}
