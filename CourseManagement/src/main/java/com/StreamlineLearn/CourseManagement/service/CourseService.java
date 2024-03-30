package com.StreamlineLearn.CourseManagement.service;

import com.StreamlineLearn.CourseManagement.model.Course;

import java.util.List;

public interface CourseService {
    void createCourse(Course course, String token);

    List<Course> getAllTheCourse();

    Course getCourseById(Long id);

    boolean updateCourseById(Long id,Course course);

    boolean deleteCourseById(Long id);
}
