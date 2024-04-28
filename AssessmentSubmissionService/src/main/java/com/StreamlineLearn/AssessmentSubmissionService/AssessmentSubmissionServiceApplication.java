package com.StreamlineLearn.AssessmentSubmissionService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.StreamlineLearn.AssessmentSubmissionService", "com.StreamlineLearn"})
public class AssessmentSubmissionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssessmentSubmissionServiceApplication.class, args);
	}

}
