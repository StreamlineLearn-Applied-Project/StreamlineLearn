package com.StreamlineLearn.CourseManagement.repository;

import com.StreamlineLearn.CourseManagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByStudentId(Long studentId);
}
