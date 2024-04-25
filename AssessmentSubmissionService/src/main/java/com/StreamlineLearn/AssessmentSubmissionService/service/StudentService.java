package com.StreamlineLearn.AssessmentSubmissionService.service;


import com.StreamlineLearn.AssessmentSubmissionService.model.Student;
import com.StreamlineLearn.SharedModule.dto.EnrolledStudentDto;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;

public interface StudentService {
    public Student findStudentByStudentId(Long studentId);
    public Student saveStudent(UserSharedDto userDtoEvent);
    void enrollStudent(EnrolledStudentDto enrolledStudentDto);
}
