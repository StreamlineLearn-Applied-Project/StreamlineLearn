package com.StreamlineLearn.AssessmentSubmissionService.service;

import com.StreamlineLearn.SharedModule.dto.CourseAssessmentDto;
import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;
import com.StreamlineLearn.SharedModule.dto.EnrolledStudentDto;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaConsumerService {
    void consumeCourseDetails(CourseSharedDto courseSharedDto) throws JsonProcessingException;

    void consumeInstructorDetails(UserSharedDto userDtoEvent) throws JsonProcessingException;
    void consumeEnrolledStudentDetails(EnrolledStudentDto enrolledStudentDto) throws JsonProcessingException;
    void consumeCourseAssessmentDetails(CourseAssessmentDto courseAssessmentDto) throws JsonProcessingException;
}
