package com.example.swproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Review 에 created_at 속성을 추가하지 않은 상태라, JPA 를 통한 자동 추가.
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableJpaAuditing


@SpringBootApplication
public class SwprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwprojectApplication.class, args);
	}

}