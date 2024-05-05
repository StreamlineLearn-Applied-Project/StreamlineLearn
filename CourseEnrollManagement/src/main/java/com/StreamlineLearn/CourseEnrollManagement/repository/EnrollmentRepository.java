package com.StreamlineLearn.CourseEnrollManagement.repository;

import com.StreamlineLearn.CourseEnrollManagement.model.Course;
import com.StreamlineLearn.CourseEnrollManagement.model.Enrollment;
import com.StreamlineLearn.CourseEnrollManagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);

    boolean existsByStudentAndCourse(Student student, Course course);
}

