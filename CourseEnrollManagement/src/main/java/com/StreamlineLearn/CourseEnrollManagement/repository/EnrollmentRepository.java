package com.StreamlineLearn.CourseEnrollManagement.repository;

import com.StreamlineLearn.CourseEnrollManagement.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}

