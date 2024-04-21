package com.StreamlineLearn.AssessmentManagement.service;

import com.StreamlineLearn.AssessmentManagement.model.Instructor;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;

public interface InstructorService {
    public Instructor findInstructorById(Long id);

    void saveInstructor(UserSharedDto userDtoEvent);


}
