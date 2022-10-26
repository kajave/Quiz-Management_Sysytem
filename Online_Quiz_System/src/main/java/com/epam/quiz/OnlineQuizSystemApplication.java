package com.epam.quiz;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Quiz Mangement System",version = "1.0",description = "User Can take Quiz/Admin can make crud Operation of Quizzes"))
public class OnlineQuizSystemApplication{

	public static void main(String[] args) {
		SpringApplication.run(OnlineQuizSystemApplication.class, args);

	}

	@Bean
	public ModelMapper getInstace() {
		return new ModelMapper();
	}


	
	
}
