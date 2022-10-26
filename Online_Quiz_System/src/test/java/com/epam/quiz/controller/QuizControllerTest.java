package com.epam.quiz.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.quiz.exception.QuizException;
import com.epam.quiz.model.Question;
import com.epam.quiz.model.QuestionDTO;
import com.epam.quiz.model.Quiz;
import com.epam.quiz.model.QuizDTO;
import com.epam.quiz.restcontoller.QuizController;
import com.epam.quiz.service.QuestionService;
import com.epam.quiz.service.QuizService;
import com.epam.quiz.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
class QuizControllerTest {

	@MockBean
	QuizService quizService;

	@MockBean
	QuestionService questionService;

	@InjectMocks
	QuizController quizController;

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	JwtUtil jwtUtil;

	ModelMapper modelMapper = new ModelMapper();
	private static ObjectMapper mapper = new ObjectMapper();
	private String jwt;

	@BeforeEach
	void initialize() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ADMIN"));
		UserDetails userDetails = new org.springframework.security.core.userdetails.User("Admin", "Admin", authorities);
		jwt = jwtUtil.generateToken(userDetails);
	}

	@Test
	void test_changeNameInput() throws Exception {
		Quiz quiz = new Quiz();
		quiz.setQuizName("java");
		quiz.setList(Arrays.asList(new Question()));
		when(quizService.changeQuizName("java", "javat")).thenReturn(quiz);
		mockMvc.perform(put("/quiz/java/javat").header("Authorization", "Bearer " + jwt)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string("Quiz Name updated succesfully"));
	}

	@Test
	void test_changeNameInput_getException() throws Exception {
		Quiz quiz = new Quiz();
		quiz.setQuizName("java");
		quiz.setList(Arrays.asList(new Question()));
		when(quizService.changeQuizName("java", "j")).thenThrow(QuizException.class);
		mockMvc.perform(put("/quiz/java/j").header("Authorization", "Bearer " + jwt)).andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof QuizException));

	}

	@Test
	void test_deleteQuiz() throws Exception {
		when(quizService.deleteQuize("java")).thenReturn(1);
		mockMvc.perform(delete("/quiz/java").header("Authorization", "Bearer " + jwt)).andExpect(status().isOk()).andDo(print())
				.andExpect(content().string("Quiz Delete succesfully"));

	}

	@Test
	void test_deleteQuiz_getException() throws Exception {
		when(quizService.deleteQuize("java")).thenThrow(QuizException.class);
		mockMvc.perform(delete("/quiz/java").header("Authorization", "Bearer " + jwt)).andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof Exception));

	}

	@Test
	void test_addQuestion_sccesful() throws Exception {

		QuizDTO quizDTO = new QuizDTO();
		quizDTO.setQuizName("java");
		quizDTO.setList(Arrays.asList(new QuestionDTO()));

		when(quizService.add(any(QuizDTO.class))).thenReturn(quizDTO);
		String json = mapper.writeValueAsString(quizDTO);
		mockMvc.perform(post("/quiz").header("Authorization", "Bearer " + jwt).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk())
				.andExpect(content().json(json));
	}

	@Test
	void test_addQuestion_fail() throws Exception {

		QuizDTO quizDTO = new QuizDTO();
		quizDTO.setQuizName("java");
		quizDTO.setList(Arrays.asList(new QuestionDTO()));

		when(quizService.add(any(QuizDTO.class))).thenThrow(QuizException.class);
		String json = mapper.writeValueAsString(quizDTO);
		mockMvc.perform(post("/quiz").header("Authorization", "Bearer " + jwt).contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof QuizException));
	}

	@Test
	void test_showName() throws Exception {
		List<String> strings = Arrays.asList("java", "python");
		when(quizService.getQuizNames()).thenReturn(strings);
		mockMvc.perform(get("/quiz/").header("Authorization", "Bearer " + jwt)).andDo(print()).andExpect(status().isOk());

	}

	@Test
	void test_showName_getException() throws Exception {
		when(quizService.getQuizNames()).thenThrow(QuizException.class);
		mockMvc.perform(get("/quiz/").header("Authorization", "Bearer " + jwt)).andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof QuizException));

	}

	@Test
	void test_displayQuiz() throws Exception {
		QuizDTO quiz = new QuizDTO();
		quiz.setQuizName("java");
		quiz.setList(Arrays.asList(new QuestionDTO()));
		when(quizService.getQuiz("java")).thenReturn(quiz);
		mockMvc.perform(get("/quiz/java").header("Authorization", "Bearer " + jwt)).andExpect(status().isOk());
	}

	@Test
	void test_displayQuiz_getException() throws Exception {
		Quiz quiz = new Quiz();
		quiz.setQuizName("java");
		quiz.setList(Arrays.asList(new Question()));
		when(quizService.getQuiz("java")).thenThrow(QuizException.class);
		mockMvc.perform(get("/quiz/java").header("Authorization", "Bearer " + jwt)).andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof QuizException));
	}

}
