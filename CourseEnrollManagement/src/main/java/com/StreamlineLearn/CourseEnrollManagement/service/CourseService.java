package com.StreamlineLearn.CourseEnrollManagement.service;



import com.StreamlineLearn.CourseEnrollManagement.model.Course;
import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;

public interface CourseService {
    Course getCourseByCourseId(Long id);
    public void saveCourse(CourseSharedDto courseDtoEvent);
}
