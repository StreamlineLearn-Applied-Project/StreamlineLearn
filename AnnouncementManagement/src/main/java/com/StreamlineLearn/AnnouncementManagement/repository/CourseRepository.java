package com.StreamlineLearn.AnnouncementManagement.repository;

import com.StreamlineLearn.AnnouncementManagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
