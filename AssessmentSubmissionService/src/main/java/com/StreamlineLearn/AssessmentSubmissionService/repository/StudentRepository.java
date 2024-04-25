package com.StreamlineLearn.AssessmentSubmissionService.repository;


import com.StreamlineLearn.AssessmentSubmissionService.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
