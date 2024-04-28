package com.StreamlineLearn.CourseEnrollManagement.controller;

import com.StreamlineLearn.CourseEnrollManagement.model.Course;
import com.StreamlineLearn.CourseEnrollManagement.model.Enrollment;
import com.StreamlineLearn.CourseEnrollManagement.model.Student;
import com.StreamlineLearn.CourseEnrollManagement.service.EnrollmentService;
import com.StreamlineLearn.SharedModule.annotation.IsStudent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses/{courseId}/enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    @IsStudent
    public ResponseEntity<String> enroll(@PathVariable("courseId") Long courseId,
                                         @RequestHeader("Authorization") String token) {
        Boolean enrolled = enrollmentService.enrollStudent(token, courseId);
        if (enrolled) {
            return new ResponseEntity<>("Student Enrolled Successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Unable to enroll student", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check/{studentId}")
    public ResponseEntity<String> checkEnrollment(@PathVariable("courseId") Long courseId,
                                                  @PathVariable("studentId") Long studentId) {
        Boolean isPaid = enrollmentService.isStudentPaid(studentId, courseId);
        if (isPaid) {
            return new ResponseEntity<>("PAID", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Student has not paid for this course", HttpStatus.NOT_FOUND);
        }
    }


}


