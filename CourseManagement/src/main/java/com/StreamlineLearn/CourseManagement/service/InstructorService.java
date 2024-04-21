package com.StreamlineLearn.CourseManagement.service;

import com.StreamlineLearn.CourseManagement.model.Instructor;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;

public interface InstructorService {
    public Instructor findInstructorById(Long id);

    void saveInstructor(UserSharedDto userDtoEvent);


}
