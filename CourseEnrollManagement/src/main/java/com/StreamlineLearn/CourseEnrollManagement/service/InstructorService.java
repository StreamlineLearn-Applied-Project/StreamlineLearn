package com.StreamlineLearn.CourseEnrollManagement.service;


import com.StreamlineLearn.CourseEnrollManagement.model.Instructor;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;

public interface InstructorService {
    public Instructor findInstructorById(Long id);

    void saveInstructor(UserSharedDto userDtoEvent);


}
