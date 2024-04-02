package com.StreamlineLearn.CourseManagement.service;

import com.StreamlineLearn.CourseManagement.model.Instructor;
import com.StreamlineLearn.CourseManagement.model.Student;

public interface StudentService {
    public Student findStudentByInstructorId(Long studentId);

    Student saveStudent(String token);
}
