package com.StreamlineLearn.AssessmentSubmissionService.repository;


import com.StreamlineLearn.AssessmentSubmissionService.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
