package com.StreamlineLearn.FeedbackManagment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.StreamlineLearn.FeedbackManagment", "com.StreamlineLearn.SharedModule"})
public class FeedbackManagmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedbackManagmentApplication.class, args);
	}

}
