package com.StreamlineLearn.ContentManagement.service;


import com.StreamlineLearn.ContentManagement.model.Course;
import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;

public interface CourseService {
    Course getCourseByCourseId(Long id);
    public void saveCourse(CourseSharedDto courseDtoEvent);
    public void saveCourse(Course course);
    boolean isInstructorOfCourse(Long instructorId, Long courseId);
}
