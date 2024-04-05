package com.StreamlineLearn.FeedbackManagment.service;



import com.StreamlineLearn.FeedbackManagment.model.Student;
import com.StreamlineLearn.SharedModule.dto.UserDto;

public interface StudentService {
    public Student findStudentByStudentId(Long studentId);
    public Student saveStudent(UserDto userDtoEvent);
}
