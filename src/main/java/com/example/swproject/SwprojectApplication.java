package com.example.swproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SwprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwprojectApplication.class, args);
	}

}