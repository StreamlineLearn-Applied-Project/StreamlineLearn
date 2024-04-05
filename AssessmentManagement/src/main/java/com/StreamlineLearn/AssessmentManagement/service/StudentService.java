package com.StreamlineLearn.AssessmentManagement.service;

import com.StreamlineLearn.AssessmentManagement.model.Student;
import com.StreamlineLearn.SharedModule.dto.UserDto;

public interface StudentService {
    public Student findStudentByStudentId(Long studentId);
    public Student saveStudent(UserDto userDtoEvent);
}
