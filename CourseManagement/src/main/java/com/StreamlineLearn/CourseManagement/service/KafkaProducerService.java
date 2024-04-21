package com.StreamlineLearn.CourseManagement.service;

import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;

public interface KafkaProducerService {
    void publishCourseDetails(CourseSharedDto courseSharedDto);
}
