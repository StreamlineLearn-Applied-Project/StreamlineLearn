package com.StreamlineLearn.DiscussionService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.StreamlineLearn.DiscussionService", "com.StreamlineLearn.SharedModule"})
public class DiscussionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscussionServiceApplication.class, args);
	}

}
