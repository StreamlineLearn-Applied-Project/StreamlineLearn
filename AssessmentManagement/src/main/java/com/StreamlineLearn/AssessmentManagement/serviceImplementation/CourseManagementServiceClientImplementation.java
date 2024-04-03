package com.StreamlineLearn.AssessmentManagement.serviceImplementation;

import com.StreamlineLearn.AssessmentManagement.external.CourseExternal;
import com.StreamlineLearn.AssessmentManagement.service.CourseManagementServiceClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CourseManagementServiceClientImplementation implements CourseManagementServiceClient {
    private final RestTemplate restTemplate;


    public CourseManagementServiceClientImplementation(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CourseExternal getCourseById(Long courseId) {
        String url = String.format("http://localhost:8282/courses/%s", courseId);
        return restTemplate.getForObject(url, CourseExternal.class);
    }

}
