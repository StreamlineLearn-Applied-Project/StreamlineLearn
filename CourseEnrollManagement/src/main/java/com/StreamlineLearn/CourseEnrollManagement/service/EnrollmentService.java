package com.StreamlineLearn.CourseEnrollManagement.service;

public interface EnrollmentService {
    Boolean enrollStudent(String token, Long courseId);

    Boolean isStudentPaid(Long studentId, Long courseId);
}
