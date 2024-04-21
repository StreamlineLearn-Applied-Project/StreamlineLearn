package com.StreamlineLearn.ContentManagement.repository;

import com.StreamlineLearn.ContentManagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
