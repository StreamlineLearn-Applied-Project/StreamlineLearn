package com.StreamlineLearn.CourseEnrollManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.StreamlineLearn.CourseEnrollManagement", "com.StreamlineLearn.SharedModule"})
public class CourseEnrollManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseEnrollManagementApplication.class, args);
	}

}
