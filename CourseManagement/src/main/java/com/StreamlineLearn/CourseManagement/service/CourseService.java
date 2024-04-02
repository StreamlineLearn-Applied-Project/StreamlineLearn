package com.StreamlineLearn.CourseManagement.service;

import com.StreamlineLearn.CourseManagement.model.Course;

import java.util.List;

public interface CourseService {
    void createCourse(Course course, String token);

    void enrollStudent(Long courseId, String token);

    Course getCourseContent(Long courseId, String token);

    public boolean isStudentEnrolled(Long courseId, Long studentId);

    List<Course> getAllTheCourse();

    Course getCourseById(Long id);

    boolean updateCourseById(Long id,Course course);

    boolean deleteCourseById(Long id);

}
