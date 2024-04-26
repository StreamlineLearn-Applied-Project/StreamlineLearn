package com.StreamlineLearn.CourseManagement.service;

import com.StreamlineLearn.CourseManagement.dto.CourseDTO;
import com.StreamlineLearn.CourseManagement.model.Course;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseService {
    void createCourse(Course course, String token);
    List<Course> getAllTheCourse();
    Set<Course> getAllInstructorCourse(String authorizationHeader);
    Optional<CourseDTO> getCourseById(Long id, String token);
    boolean updateCourseById(Long id,Course course, String token);
    boolean deleteCourseById(Long id, String token);

}
