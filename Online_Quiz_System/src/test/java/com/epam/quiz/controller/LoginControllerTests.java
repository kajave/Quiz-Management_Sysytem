package com.epam.quiz.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.quiz.exception.UserException;
import com.epam.quiz.model.User;
import com.epam.quiz.model.UserDTO;
import com.epam.quiz.restcontoller.AuthController;
import com.epam.quiz.restcontoller.LoginController;
import com.epam.quiz.service.AppUserDetailsService;
import com.epam.quiz.service.LoginService;
import com.epam.quiz.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

//@WebMvcTest(LoginController.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
class LoginControllerTests {

	@MockBean
	LoginService loginService;

	@MockBean
	AuthController authController;

	@MockBean
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil jwtUtil;

	@MockBean
	AppUserDetailsService appUserDetailsService;

	@InjectMocks
	LoginController loginController;

	@Autowired
	MockMvc mockMvc;

	@Mock
	private AuthenticationManager authenticationManager1;

	@Mock
	private AppUserDetailsService userDetailsService;

	@Mock
	private JwtUtil jwtTokenUtil;

	ModelMapper modelMapper = new ModelMapper();
	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	void test_registerUser() throws Exception {
		UserDTO userDTO = new UserDTO("Admin", "123");
		when(loginService.add(modelMapper.map(userDTO, User.class))).thenReturn(modelMapper.map(userDTO, User.class));

		String json = mapper.writeValueAsString(userDTO);
		mockMvc.perform(post("/login/user/register").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andExpect(content().string("User register succesfully"));
	}

	@Test
	void test_RegisterUser_fail() throws Exception {
		UserDTO userDTO = new UserDTO("aniket", "");
		when(loginService.add(any(User.class))).thenThrow(UserException.class);

		String json = mapper.writeValueAsString(userDTO);
		mockMvc.perform(post("/login/user/register").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().is4xxClientError())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof UserException));

	}

	@Test
	void test_createAuthenticationToken() throws Exception {
		Authentication au = new UsernamePasswordAuthenticationToken("Aniket", "123");
		when(authenticationManager1.authenticate(au)).thenReturn(au);

		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ADMIN"));
		UserDetails userDetails = new org.springframework.security.core.userdetails.User("Aniket", "123", authorities);

		when(userDetailsService.loadUserByUsername("Aniket")).thenReturn(userDetails);
		String jwt = "token";
		when(jwtTokenUtil.generateToken(userDetails)).thenReturn(jwt);

		UserDTO userDTO = new UserDTO("Aniket", "123");
		String json = mapper.writeValueAsString(userDTO);
		mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print())
				.andExpect(status().isOk());

	}

}
