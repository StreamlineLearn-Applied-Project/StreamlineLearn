package com.StreamlineLearn.CourseManagement.service;

import com.StreamlineLearn.CourseManagement.model.Instructor;
import com.StreamlineLearn.SharedModule.dto.UserDto;

public interface InstructorService {

    public Instructor findInstructorByInstructorId(Long instructorId);

    void saveInstructor(UserDto userDtoEvent);
}
