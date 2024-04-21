package com.StreamlineLearn.AssessmentManagement.repository;

import com.StreamlineLearn.AssessmentManagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
