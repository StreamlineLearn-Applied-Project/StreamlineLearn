package com.StreamlineLearn.CourseManagement.service;

import com.StreamlineLearn.CourseManagement.dto.CourseDTO;
import com.StreamlineLearn.CourseManagement.model.Course;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseService {
    Course createCourse(Course course, MultipartFile file, String token);
    List<Course> getAllTheCourse();
    Set<Course> getAllInstructorCourse(String authorizationHeader);
    Optional<Course> getCourseById(Long id, String token);
    byte[] getCourseMedia(Long courseId, String fileName) throws IOException;
    boolean updateCourseById(Long id,Course course, MultipartFile file, String token);
    boolean deleteCourseById(Long id, String token);

}
