package com.StreamlineLearn.CourseManagement.service;

import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaConsumerService {
    void consumeInstructorDetails(UserSharedDto userDtoEvent) throws JsonProcessingException;
}
