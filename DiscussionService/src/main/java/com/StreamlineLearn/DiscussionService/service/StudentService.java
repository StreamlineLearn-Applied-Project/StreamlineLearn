package com.StreamlineLearn.DiscussionService.service;

import com.StreamlineLearn.DiscussionService.model.Student;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;

public interface StudentService {
    public Student findStudentByStudentId(Long studentId);
    public Student saveStudent(UserSharedDto userDtoEvent);
}
