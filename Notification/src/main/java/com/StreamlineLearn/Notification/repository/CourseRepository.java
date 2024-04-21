package com.StreamlineLearn.Notification.repository;


import com.StreamlineLearn.Notification.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
