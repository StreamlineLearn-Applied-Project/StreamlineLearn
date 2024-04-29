package com.StreamlineLearn.AssessmentManagement.service;


import com.StreamlineLearn.AssessmentManagement.model.Course;
import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;

public interface CourseService {
    Course getCourseByCourseId(Long id);
    public void saveCourse(CourseSharedDto courseDtoEvent);
    boolean isInstructorOfCourse(Long instructorId, Long courseId);

    public void saveCourse(Course course);
}
