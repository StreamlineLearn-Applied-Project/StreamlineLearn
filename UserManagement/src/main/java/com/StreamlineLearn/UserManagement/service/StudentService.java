package com.StreamlineLearn.UserManagement.service;

import com.StreamlineLearn.UserManagement.model.Student;

import java.util.List;

public interface StudentService {
    void createStudent(Student student);

    List<Student> getAllUser();

    Student getUserById(Long id);

    boolean updateStudent(Long id, Student updateStudent);

    boolean deleteStudentById(Long id);
}
