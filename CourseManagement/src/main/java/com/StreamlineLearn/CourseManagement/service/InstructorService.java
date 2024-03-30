package com.StreamlineLearn.CourseManagement.service;

import com.StreamlineLearn.CourseManagement.model.Instructor;

public interface InstructorService {

    public Instructor findInstructorByInstructorId(Long instructorId);

    Instructor saveInstructor(String token);
}
