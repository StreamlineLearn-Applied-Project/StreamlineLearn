package com.StreamlineLearn.CourseManagement.repository;

import com.StreamlineLearn.CourseManagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
