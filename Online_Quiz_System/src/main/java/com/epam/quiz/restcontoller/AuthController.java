package com.epam.quiz.restcontoller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.epam.quiz.model.UserDTO;
import com.epam.quiz.service.AppUserDetailsService;
import com.epam.quiz.util.JwtUtil;



@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private AppUserDetailsService userDetailsService;
	
	@Autowired
	ModelMapper mapper;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public String createAuthenticationToken(@RequestBody UserDTO userDTO)
			throws Exception {
                 
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					userDTO.getUsername(), userDTO.getPassword()));
			
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
			
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());

		return jwtTokenUtil.generateToken(userDetails);
	}
}