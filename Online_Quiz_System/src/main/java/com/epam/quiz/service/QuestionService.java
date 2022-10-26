package com.epam.quiz.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.quiz.exception.QuestionException;
import com.epam.quiz.exception.QuizException;
import com.epam.quiz.model.Question;
import com.epam.quiz.model.QuestionDTO;
import com.epam.quiz.model.QuizDTO;
import com.epam.quiz.repository.QuestionRepository;

@Service
public class QuestionService {
	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	QuizService quizService;

	@Autowired
	ModelMapper mapper;

	public QuestionDTO add(QuestionDTO questionDTO) throws QuestionException, QuizException {
		QuizDTO quiz = quizService.getQuiz(questionDTO.getQuizname());
		questionDTO.setQuiz(quiz);
		Question question = mapper.map(questionDTO, Question.class);
		Optional<Question> quOptional = Optional.empty();
		try {
			quOptional = Optional.of(questionRepository.save(question));
		} catch (RuntimeException e) {
			throw new QuestionException("Question is not Addede suceesfully");
		}
		question = quOptional.orElseThrow(() -> new QuestionException("Question is not Addede suceesfully"));
		return mapper.map(question, QuestionDTO.class);
	}

	@Transactional
	public int delete(String name) throws QuestionException {
		int updateCount = 0;
		try {
			updateCount = questionRepository.deleteByName(name);
		} catch (RuntimeException e) {
			throw new QuestionException("Question not deleted suceesfully");
		}
		if (updateCount == 0) {
			throw new QuestionException("Question is not deleted sussefully");
		}
		return updateCount;
	}

	
	public List<QuestionDTO> get() throws QuestionException {
		Optional<List<Question>> quOptional = Optional.empty();
		try {
			quOptional = Optional.of(questionRepository.findAll());
		} catch (RuntimeException e) {
			throw new QuestionException("Question is not present");
		}
		List<Question> questions = quOptional.orElseThrow(() -> new QuestionException("Question is not present"));
		return questions.stream().map(q -> mapper.map(q, QuestionDTO.class)).collect(Collectors.toList());

	}

	public QuestionDTO getById(int id) throws QuestionException {

		Question question = new Question();
		try {
			question = questionRepository.findById(id).get();
		} catch (RuntimeException e) {
			throw new QuestionException("Question is not present");
		}
		return mapper.map(question, QuestionDTO.class);
	}

}
