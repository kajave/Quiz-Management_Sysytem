package com.epam.quiz.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.quiz.exception.QuizException;
import com.epam.quiz.model.Question;
import com.epam.quiz.model.QuestionDTO;
import com.epam.quiz.model.Quiz;
import com.epam.quiz.model.QuizDTO;
import com.epam.quiz.repository.QuizRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class QuizServiceTest {

	@Mock
	QuizRepository quizRepository;
	
	@Mock
	ModelMapper mapper;

	@InjectMocks
	QuizService quizService;

	@Test
	void test_addQuiz() throws QuizException {
		QuizDTO quizDTO = new QuizDTO("java", Arrays.asList(new QuestionDTO()));
		Quiz quiz = new Quiz("java", Arrays.asList(new Question()));
		
		when(mapper.map(quizDTO, Quiz.class)).thenReturn(quiz);
		when(quizRepository.save(quiz)).thenReturn(quiz);
		when(mapper.map(quiz, QuizDTO.class)).thenReturn(quizDTO);

		assertEquals(quizDTO, quizService.add(quizDTO));
		assertDoesNotThrow(() -> quizService.add(quizDTO));
	}

	@Test
	void test_addQuiz_throwException() throws QuizException {
		QuizDTO quizDTO = new QuizDTO("java", Arrays.asList(new QuestionDTO()));
		Quiz quiz = new Quiz("java", Arrays.asList(new Question()));
		
		when(mapper.map(quizDTO, Quiz.class)).thenReturn(quiz);
		when(quizRepository.save(quiz)).thenThrow(IllegalStateException.class);
		assertThrows(QuizException.class, () -> quizService.add(quizDTO));
		
//		when(quizRepository.save(quiz)).thenReturn(null);
//		assertThrows(QuizException.class, () -> quizService.add(any(QuizDTO.class)));

	}

	@Test
	void test_findQuizQuestion_getQuestion() throws QuizException {
		Optional<Quiz> quiz = Optional.ofNullable(new Quiz());
		quiz.get().setList(Arrays.asList(new Question(), new Question()));
		when(quizRepository.findByQuizName("java")).thenReturn(quiz);
		assertEquals(2, quizService.getQuizQuestions("java").size());

	}

	@Test
	void test_findQuizQuestion_getException() {
		when(quizRepository.findByQuizName("java")).thenThrow(IllegalStateException.class);
		assertThrows(QuizException.class, () -> quizService.getQuizQuestions("java"));

	}

	@Test
	void test_getQuizNames() throws QuizException {
		when(quizRepository.getQuizName()).thenReturn(Arrays.asList("java", "python"));
		assertEquals(2, quizService.getQuizNames().size());
	}

	@Test
	void test_getQuizNames_getException() {
		when(quizRepository.getQuizName()).thenThrow(NoSuchElementException.class);
		assertThrows(QuizException.class, () -> quizService.getQuizNames());
	}

	@Test
	void test_deleteQuiz() throws QuizException {
		when(quizRepository.deleteByQuizName("java")).thenReturn(1);
		assertEquals(1, quizService.deleteQuize("java"));
	}

	@Test
	void test_deleteQuiz_getException() {
		when(quizRepository.deleteByQuizName("java")).thenThrow(NoSuchElementException.class);
		assertThrows(QuizException.class, () -> quizService.deleteQuize("java"));
	}
	

	@Test
	void test_getQuiz() throws QuizException {
		QuizDTO q= new QuizDTO();
		q.setQuizName("java");
		Optional<Quiz> quiz = Optional.ofNullable(new Quiz());
		when(quizRepository.findByQuizName("java")).thenReturn(quiz);
		when(mapper.map(quiz.get(), QuizDTO.class)).thenReturn(q);
		assertEquals(q, quizService.getQuiz("java"));
	}

	@Test
	void test_findQuiz_succesfull() throws QuizException {
		Optional<Quiz> quiz = Optional.ofNullable(new Quiz());
		quiz.get().setList(Arrays.asList(new Question(), new Question()));
		when(quizRepository.findByQuizName(anyString())).thenReturn(quiz);
		assertNotNull(quizService.getQuiz("java"));
		assertEquals(2, quizService.getQuiz("java").getList().size());

	}
	
	@Test
	void test_findQuiz_fail() throws QuizException {
		Optional<Quiz> quiz = Optional.empty();
		when(quizRepository.findByQuizName("java")).thenReturn(quiz);
		assertThrows(QuizException.class, ()->quizService.getQuiz("java"));

	}

	@Test
	void test_findQuiz_getException() {
		when(quizRepository.findByQuizName("java")).thenThrow(NoSuchElementException.class);
		assertThrows(QuizException.class, () -> quizService.getQuiz("java"));

	}

	@Test
	void test_changeQuizName() {
		Optional<Quiz> quiz = Optional.ofNullable(new Quiz());
		quiz.get().setQuizName("java");
		when(quizRepository.findByQuizName("java")).thenReturn(quiz);
		assertDoesNotThrow(() -> quizService.changeQuizName("java", "javat"));

	}

	@Test
	void test_changeQuizName_getException() {
		Optional<Quiz> quiz1 = Optional.empty();
		when(quizRepository.findByQuizName("java")).thenReturn(quiz1);
		assertThrows(QuizException.class, () -> quizService.changeQuizName("java", "javat"));
		
		when(quizRepository.findByQuizName("java")).thenReturn(quiz1.empty());
		assertThrows(QuizException.class, () -> quizService.changeQuizName("java", "javat"));
	}



}
