package com.StreamlineLearn.ContentManagement.service;

import com.StreamlineLearn.ContentManagement.model.Instructor;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;

public interface InstructorService {
    public Instructor findInstructorById(Long id);

    void saveInstructor(UserSharedDto userDtoEvent);


}
