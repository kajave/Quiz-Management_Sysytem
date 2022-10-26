package com.epam.quiz.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExcepionHandler {
	
	@ExceptionHandler(value = QuestionException.class)
	public ResponseEntity<ExceptionResponce> handleQuestionException(QuestionException exception,WebRequest request) {
		 ExceptionResponce exceptionResponse = new ExceptionResponce();
	        exceptionResponse.setError(exception.getName());
	        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
	        exceptionResponse.setTimeStamp(LocalDate.now().toString());
	        exceptionResponse.setPath(request.getDescription(false));
	        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(value = QuizException.class)
	public ResponseEntity<ExceptionResponce> handleQuizNotFoundException(QuizException exception,WebRequest request) {
		 ExceptionResponce exceptionResponse = new ExceptionResponce();
	        exceptionResponse.setError(exception.getName());
	        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
	        exceptionResponse.setTimeStamp(LocalDate.now().toString());
	        exceptionResponse.setPath(request.getDescription(false));
	        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(value = UserException.class)
	public ResponseEntity<ExceptionResponce> handleUserNOtException(UserException exception,WebRequest request) {
		 ExceptionResponce exceptionResponse = new ExceptionResponce();
	        exceptionResponse.setError(exception.getName());
	        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
	        exceptionResponse.setTimeStamp(LocalDate.now().toString());
	        exceptionResponse.setPath(request.getDescription(false));
	        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
		
	}

}
