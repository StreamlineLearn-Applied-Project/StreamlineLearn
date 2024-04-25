package com.StreamlineLearn.AssessmentSubmissionService.service;

import com.StreamlineLearn.AssessmentSubmissionService.model.Instructor;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;

public interface InstructorService {
    public Instructor findInstructorById(Long id);

    void saveInstructor(UserSharedDto userDtoEvent);


}
