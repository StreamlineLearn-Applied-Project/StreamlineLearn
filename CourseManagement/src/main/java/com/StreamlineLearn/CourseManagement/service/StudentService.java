package com.StreamlineLearn.CourseManagement.service;


import com.StreamlineLearn.CourseManagement.model.Student;
import com.StreamlineLearn.SharedModule.dto.UserDto;

public interface StudentService {
    public Student findStudentByStudentId(Long studentId);
    public Student saveStudent(UserDto userDtoEvent);
}
