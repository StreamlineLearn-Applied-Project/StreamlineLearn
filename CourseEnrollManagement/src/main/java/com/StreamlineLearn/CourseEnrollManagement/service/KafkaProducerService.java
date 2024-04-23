package com.StreamlineLearn.CourseEnrollManagement.service;

import com.StreamlineLearn.SharedModule.dto.EnrolledStudentDto;

public interface KafkaProducerService {
    void publishEnrollStudentDetails(EnrolledStudentDto enrolledStudentDto);
}
