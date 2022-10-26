package com.epam.quiz.exception;

import java.lang.annotation.Repeatable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExcepionHandler {

	
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleException(Exception exception) {
		ModelAndView modelAndView = new ModelAndView("Error");
		modelAndView.addObject("message", exception.getMessage());
		return modelAndView;
	}

}
