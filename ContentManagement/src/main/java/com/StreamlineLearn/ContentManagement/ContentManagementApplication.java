package com.StreamlineLearn.ContentManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.StreamlineLearn.ContentManagement", "com.StreamlineLearn.SharedModule"})
public class ContentManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentManagementApplication.class, args);
	}

}
