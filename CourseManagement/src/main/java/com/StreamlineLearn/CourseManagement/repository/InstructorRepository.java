package com.StreamlineLearn.CourseManagement.repository;

import com.StreamlineLearn.CourseManagement.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

}
