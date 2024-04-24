package com.StreamlineLearn.Notification.service;

import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;
import com.StreamlineLearn.SharedModule.dto.EnrolledStudentDto;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaConsumerService {
    public void consumeInstructorDetails(UserSharedDto userDtoEvent) throws JsonProcessingException;
    void consumeStudentDetails(UserSharedDto studentDetails) throws JsonProcessingException;
    void consumeCourseDetails(CourseSharedDto courseSharedDto) throws JsonProcessingException;
    void consumeEnrolledStudentDetails(EnrolledStudentDto enrolledStudentDto) throws JsonProcessingException;
}
