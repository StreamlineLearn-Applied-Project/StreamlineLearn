package com.StreamlineLearn.CourseEnrollManagement.repository;

import com.StreamlineLearn.CourseEnrollManagement.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

}
