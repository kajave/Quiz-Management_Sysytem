package com.epam.quiz.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.quiz.exception.QuizException;
import com.epam.quiz.model.Question;
import com.epam.quiz.model.Quiz;
import com.epam.quiz.model.QuizDTO;
import com.epam.quiz.repository.QuizRepository;

@Service
public class QuizService {

	@Autowired
	QuizRepository quizRepository;

	@Autowired
	ModelMapper mapper;

	public QuizDTO add(QuizDTO quizDTO) throws QuizException {
		if (quizDTO.getList() != null) {
			quizDTO.getList().stream().forEach(q -> q.setQuiz(quizDTO));
		}
		Quiz quiz = mapper.map(quizDTO, Quiz.class);

		Optional<Quiz> quOptional = Optional.empty();
		try {
			quOptional = Optional.of(quizRepository.save(quiz));
		} catch (RuntimeException e) {
			throw new QuizException("Quiz is not Addede suceesfully");
		}
		quiz = quOptional.orElseThrow(() -> new QuizException("Quiz is not Addede suceesfully"));
		return mapper.map(quiz, QuizDTO.class);
	}

	public List<Question> getQuizQuestions(String quizName) throws QuizException {
		Optional<Quiz> quiz = Optional.empty();
		try {
			quiz = quizRepository.findByQuizName(quizName);
		} catch (RuntimeException e) {
			throw new QuizException("Quiz is not present");
		}
		return quiz.get().getList();
	}

	public List<String> getQuizNames() throws QuizException {
		List<String> quizName = null;
		try {
			quizName = quizRepository.getQuizName();
		} catch (RuntimeException e) {
			throw new QuizException("Quiz is no Available ");
		}
		return quizName;
	}

	@Transactional
	public int deleteQuize(String name) throws QuizException {
		int updateCount = 0;
		try {
			updateCount = quizRepository.deleteByQuizName(name);
		} catch (RuntimeException e) {

			throw new QuizException("Quiz is not deleted suceesfully");
		}
		return updateCount;

	}

	public QuizDTO getQuiz(String name) throws QuizException {
		Optional<Quiz> optionalQuiz = Optional.empty();
		try {
			optionalQuiz = quizRepository.findByQuizName(name);
		} catch (RuntimeException e) {
			throw new QuizException("Quiz is not present");
		}
		Quiz q = optionalQuiz.orElseThrow(() -> new QuizException("Quiz is not present"));
		return mapper.map(q, QuizDTO.class);
	}

	@Transactional
	public Quiz changeQuizName(String oldName, String newName) throws QuizException {
		Optional<Quiz> quiz = Optional.empty();
		try {
			quiz = quizRepository.findByQuizName(oldName);
			quiz.get().setQuizName(newName);
		} catch (RuntimeException e) {
			throw new QuizException("Quiz is not present");
		}
		return quiz.orElseThrow(() -> new QuizException("Quiz is not present"));

	}

	

}
