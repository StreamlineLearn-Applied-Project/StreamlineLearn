package com.StreamlineLearn.AssessmentManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.StreamlineLearn.AssessmentManagement", "com.StreamlineLearn.SharedModule"})
public class AssessmentManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssessmentManagementApplication.class, args);
	}

}
