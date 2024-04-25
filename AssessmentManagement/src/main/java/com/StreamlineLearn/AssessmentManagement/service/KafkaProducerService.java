package com.StreamlineLearn.AssessmentManagement.service;

import com.StreamlineLearn.SharedModule.dto.CourseAssessmentDto;
import com.StreamlineLearn.SharedModule.dto.EnrolledStudentDto;

public interface KafkaProducerService {
    void publishCourseAssessmentDetails(CourseAssessmentDto courseAssessmentDto);
}
