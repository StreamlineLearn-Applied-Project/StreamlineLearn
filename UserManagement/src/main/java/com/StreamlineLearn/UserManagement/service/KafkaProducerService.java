package com.StreamlineLearn.UserManagement.service;

import com.StreamlineLearn.SharedModule.dto.UserSharedDto;

public interface KafkaProducerService {
    void publishInstructorDetails(UserSharedDto instructorDetails);
    void publishStudentDetails(UserSharedDto studentDetails);
    void publishAdministrativeDetails(UserSharedDto administrativeDetails);
}
