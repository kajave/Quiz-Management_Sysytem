package com.epam.quiz.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.quiz.exception.QuestionException;
import com.epam.quiz.exception.QuizException;
import com.epam.quiz.model.Question;
import com.epam.quiz.model.QuestionDTO;
import com.epam.quiz.model.QuizDTO;
import com.epam.quiz.repository.QuestionRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class QuestionServiceTest {

	@Mock
	QuestionRepository questionRepository;

	@InjectMocks
	QuestionService questionService;
	
	@Mock
	QuizService quizService;
	
	@Mock
	ModelMapper mapper;

	@Test
	void test_addQuestion() throws QuestionException, QuizException {
		QuestionDTO questionDTO = new QuestionDTO();
		questionDTO.setQuizname("java");
		Question question = new Question();
		QuizDTO quiz = new QuizDTO();
		when(mapper.map(questionDTO, Question.class)).thenReturn(question);
		when(quizService.getQuiz("java")).thenReturn(quiz);
		when(questionRepository.save(question)).thenReturn(question);
		when(mapper.map(question, QuestionDTO.class)).thenReturn(questionDTO);
		
		assertEquals(questionDTO, questionService.add(questionDTO));
		assertDoesNotThrow(() -> questionService.add(questionDTO));
	}

	@Test
	void test_addQuestion_throwException() throws QuizException {
		QuestionDTO questionDTO = new QuestionDTO();
		questionDTO.setQuizname("java");
		Question question = new Question();
		QuizDTO quiz = new QuizDTO();
		when(mapper.map(questionDTO, Question.class)).thenReturn(question);
		when(quizService.getQuiz("java")).thenReturn(quiz);
	
		when(questionRepository.save(question)).thenReturn(null);
		assertThrows(QuestionException.class, () -> questionService.add(questionDTO));

		
		when(questionRepository.save(question)).thenThrow(IllegalStateException.class);
		assertThrows(QuestionException.class, () -> questionService.add(questionDTO));
	}
	
	@Test
	void test_addQuestion_failed() throws QuizException {
		QuestionDTO questionDTO = new QuestionDTO();
		questionDTO.setQuizname("java");
		when(quizService.getQuiz("java")).thenThrow(QuizException.class);
		assertThrows(QuizException.class, () -> questionService.add(questionDTO));	
	}
	

	@Test
	void test_deleteQuiz_succesfully() throws QuestionException {
		when(questionRepository.deleteByName("java is")).thenReturn(1);
		assertEquals(1, questionService.delete("java is"));
	}
	
	@Test
	void test_deleteQuiz_fail() throws QuestionException {
		when(questionRepository.deleteByName("java is")).thenReturn(0);
		assertThrows(QuestionException.class, () -> questionService.delete("java is"));
	}
	

	@Test
	void test_deleteQuiz_getException() {
		when(questionRepository.deleteByName("java is")).thenThrow(NoSuchElementException.class);
		assertThrows(QuestionException.class, () -> questionService.delete("java is"));
	}
	
	@Test
	void test_getquestion() throws QuestionException {
		List<Question> questions=Arrays.asList(new Question(),new Question());
		when(questionRepository.findAll()).thenReturn(questions);
		
		assertEquals(2, questionService.get().size());
	
	}
	
	@Test
	void test_getquestion_getException() throws QuestionException {
		when(questionRepository.findAll()).thenThrow(RuntimeException.class);
		assertThrows(QuestionException.class, () -> questionService.get());
	
	}
	
	@Test
	void test_getquestion_empty() throws QuestionException {
		List<Question> list = null;
		when(questionRepository.findAll()).thenReturn(list);
		assertThrows(QuestionException.class, () -> questionService.get());
	
	}
	
	@Test
	void test_getquestionById() throws QuestionException {
		Question question = new Question();
		question.setName("java");
		
		QuestionDTO questionDto = new QuestionDTO();
		questionDto.setName("java");
		
		Optional<Question> quOptional = Optional.ofNullable(question);
		when(questionRepository.findById(anyInt())).thenReturn(quOptional);
		when(mapper.map(quOptional.get(), QuestionDTO.class)).thenReturn(questionDto);
		assertInstanceOf(QuestionDTO.class, questionService.getById(anyInt()));
		assertEquals("java", questionService.getById(anyInt()).getName());
	
	}
	
	@Test
	void test_getquestionById_getException() throws QuestionException {
		
		Optional<Question> quOptional = Optional.empty();
		when(questionRepository.findById(anyInt())).thenReturn(quOptional);
		assertThrows(QuestionException.class,()-> questionService.getById(anyInt()));
		
	
	}


}
