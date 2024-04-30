package com.StreamlineLearn.SharedModule.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EnrollmentService {
    private final RestTemplate restTemplate;
    private final String enrollmentApiUrl;

    public EnrollmentService(RestTemplate restTemplate,
                             @Value("${enrollment.api.url}") String enrollmentApiUrl) {
        this.restTemplate = restTemplate;
        this.enrollmentApiUrl = enrollmentApiUrl;
    }

    public boolean hasStudentPaidForCourse(Long studentId, Long courseId) {
        String apiUrl = enrollmentApiUrl + "/courses/" + courseId + "/enrollments/check/" + studentId;
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            System.out.println("API Response: " + response.getBody());
            // Assuming the API response indicates whether the student has paid for the course
            return "PAID".equals(response.getBody());
        } else {
            throw new RuntimeException("Failed to check enrollment status. API responded with status code: " + response.getStatusCode());
        }
    }
}
