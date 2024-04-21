package com.StreamlineLearn.CourseEnrollManagement.repository;

import com.StreamlineLearn.CourseEnrollManagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
