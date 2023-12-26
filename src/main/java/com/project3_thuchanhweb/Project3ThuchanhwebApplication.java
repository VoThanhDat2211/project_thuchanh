package com.project3_thuchanhweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling // lập lịch cho các task
public class Project3ThuchanhwebApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(Project3ThuchanhwebApplication.class, args);
	}

}
