package com.StreamlineLearn.AssessmentManagement.repository;

import com.StreamlineLearn.AssessmentManagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
