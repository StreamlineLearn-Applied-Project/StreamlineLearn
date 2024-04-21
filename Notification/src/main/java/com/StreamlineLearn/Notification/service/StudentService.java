package com.StreamlineLearn.Notification.service;


import com.StreamlineLearn.Notification.model.Student;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;

public interface StudentService {
    public Student findStudentByStudentId(Long studentId);
    public Student saveStudent(UserSharedDto userDtoEvent);
}
