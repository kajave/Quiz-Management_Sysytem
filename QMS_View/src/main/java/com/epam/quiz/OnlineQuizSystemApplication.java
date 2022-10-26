package com.epam.quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class OnlineQuizSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineQuizSystemApplication.class, args);

	}

	@Bean
	public WebClient.Builder getInstance(){
		return WebClient.builder();
	}
}
