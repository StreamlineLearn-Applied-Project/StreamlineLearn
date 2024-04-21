package com.StreamlineLearn.CourseEnrollManagement.service;

import com.StreamlineLearn.CourseEnrollManagement.model.Instructor;
import com.StreamlineLearn.CourseEnrollManagement.model.Student;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;

import java.util.List;

public interface StudentService {

    public Student findStudentById(Long id);
    Student saveOrGetStudent(String token);

}
