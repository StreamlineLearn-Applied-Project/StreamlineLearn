package com.StreamlineLearn.AssessmentManagement.service;

import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaConsumerService {
    void consumeCourseDetails(CourseSharedDto courseSharedDto) throws JsonProcessingException;

    void consumeInstructorDetails(UserSharedDto userDtoEvent) throws JsonProcessingException;
}
