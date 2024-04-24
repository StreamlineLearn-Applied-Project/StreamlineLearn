package com.StreamlineLearn.Notification.service;


import com.StreamlineLearn.Notification.model.Student;
import com.StreamlineLearn.SharedModule.dto.EnrolledStudentDto;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;

public interface StudentService {
    public Student findStudentById(Long id);
    void saveStudent(UserSharedDto userDtoEvent);
}
