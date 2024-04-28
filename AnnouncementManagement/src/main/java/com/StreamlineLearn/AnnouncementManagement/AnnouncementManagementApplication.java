package com.StreamlineLearn.AnnouncementManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.StreamlineLearn.AnnouncementManagement", "com.StreamlineLearn.SharedModule"})
public class AnnouncementManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnnouncementManagementApplication.class, args);
	}

}
