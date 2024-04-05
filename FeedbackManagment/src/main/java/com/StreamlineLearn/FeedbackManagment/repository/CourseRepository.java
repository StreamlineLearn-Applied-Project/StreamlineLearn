package com.StreamlineLearn.FeedbackManagment.repository;

import com.StreamlineLearn.FeedbackManagment.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseId(Long id);
}
