package com.StreamlineLearn.DiscussionService.repository;

import com.StreamlineLearn.DiscussionService.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
