package com.epam.quiz.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import com.epam.quiz.exception.QuestionException;
import com.epam.quiz.model.QuestionDTO;
import com.epam.quiz.restcontoller.QuestionController;
import com.epam.quiz.restcontoller.QuizController;
import com.epam.quiz.service.QuestionService;
import com.epam.quiz.service.QuizService;
import com.epam.quiz.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
class QuestionControllerTest {

	@MockBean
	QuizService quizService;

	@MockBean
	QuestionService questionService;

	@InjectMocks
	QuestionController questionController;

	@MockBean
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
	void test_questionUpdate() throws Exception {
		QuestionDTO questionDTO = new QuestionDTO();
		when(questionService.add(any(QuestionDTO.class))).thenReturn(any(QuestionDTO.class));
		String json = mapper.writeValueAsString(questionDTO);

		mockMvc.perform(put("/quiz/question/java").header("Authorization", "Bearer " + jwt)
				.contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string("Question update succesfully"));

	}

	@Test
	void test_questionUpdate_getException() throws Exception {
		QuestionDTO questionDTO = new QuestionDTO();
		when(questionService.add(any(QuestionDTO.class))).thenThrow(QuestionException.class);
		String json = mapper.writeValueAsString(questionDTO);

		mockMvc.perform(put("/quiz/question/java").header("Authorization", "Bearer " + jwt)
				.contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof QuestionException));

	}

	@Test
	void test_deleteQuestion() throws Exception {
		when(questionService.delete("java is")).thenReturn(1);
		mockMvc.perform(delete("/quiz/question/java is").header("Authorization", "Bearer " + jwt))
				.andExpect(status().isOk()).andExpect(content().string("Question deleted succesfully"));
	}

	@Test
	void test_deleteQuestion_getException() throws Exception {
		when(questionService.delete("java is")).thenThrow(QuestionException.class);
		mockMvc.perform(delete("/quiz/question/java is").header("Authorization", "Bearer " + jwt))
				.andExpect(status().is4xxClientError()).andDo(print())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof QuestionException));

	}

	@Test
	void test_addquestion_pass() throws Exception {
		QuestionDTO questionDTO = new QuestionDTO();
		when(questionService.add(any(QuestionDTO.class))).thenReturn(any(QuestionDTO.class));

		String json = mapper.writeValueAsString(questionDTO);
		mockMvc.perform(post("/quiz/question/java").header("Authorization", "Bearer " + jwt)
				.contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string("Question added succesfully"));
	}

	@Test
	void test_addquestion_fail() throws Exception {
		QuestionDTO questionDTO = new QuestionDTO();
		when(questionService.add(any(QuestionDTO.class))).thenThrow(QuestionException.class);
		String json = mapper.writeValueAsString(questionDTO);

		mockMvc.perform(post("/quiz/question/java").header("Authorization", "Bearer " + jwt)
				.contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof QuestionException));

	}

	@Test
	void test_displayQuestion() throws Exception {
		when(questionService.get()).thenReturn(Arrays.asList(new QuestionDTO(), new QuestionDTO()));
		mockMvc.perform(get("/quiz/question").header("Authorization", "Bearer " + jwt)).andExpect(status().isOk());
	}

	@Test
	void test_displayQuestion_getException() throws Exception {
		when(questionService.get()).thenThrow(QuestionException.class);
		mockMvc.perform(get("/quiz/question").header("Authorization", "Bearer " + jwt))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof QuestionException));
	}

	@Test
	void test_displayQuestionById() throws Exception {
		when(questionService.getById(anyInt())).thenReturn(any(QuestionDTO.class));
		mockMvc.perform(get("/quiz/question/1").header("Authorization", "Bearer " + jwt)).andExpect(status().isOk());
	}

	@Test
	void test_displayQuestionById_getException() throws Exception {
		when(questionService.getById(anyInt())).thenThrow(QuestionException.class);
		mockMvc.perform(get("/quiz/question/1").header("Authorization", "Bearer " + jwt))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof QuestionException));
	}

}
