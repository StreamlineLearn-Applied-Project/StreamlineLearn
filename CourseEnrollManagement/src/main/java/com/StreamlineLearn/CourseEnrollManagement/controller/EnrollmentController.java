package com.StreamlineLearn.CourseEnrollManagement.controller;

import com.StreamlineLearn.CourseEnrollManagement.model.Course;
import com.StreamlineLearn.CourseEnrollManagement.model.Enrollment;
import com.StreamlineLearn.CourseEnrollManagement.model.Student;
import com.StreamlineLearn.CourseEnrollManagement.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses/{courseId}/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    // constructor injection
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<String> enroll(@PathVariable("courseId") Long courseId,
                                         @RequestHeader("Authorization") String token) {
        Boolean enrolled = enrollmentService.enrollStudent(token, courseId);
        if (enrolled) {
            return new ResponseEntity<>("Student Enrolled Successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Unable to enroll student", HttpStatus.BAD_REQUEST);
        }
    }
}


