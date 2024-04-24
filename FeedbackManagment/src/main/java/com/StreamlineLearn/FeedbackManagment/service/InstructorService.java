package com.StreamlineLearn.FeedbackManagment.service;

import com.StreamlineLearn.FeedbackManagment.model.Instructor;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;

public interface InstructorService {
    public Instructor findInstructorById(Long id);

    void saveInstructor(UserSharedDto userDtoEvent);


}
