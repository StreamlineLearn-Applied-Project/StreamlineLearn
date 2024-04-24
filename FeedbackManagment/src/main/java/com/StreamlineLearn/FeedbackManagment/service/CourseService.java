package com.StreamlineLearn.FeedbackManagment.service;


import com.StreamlineLearn.FeedbackManagment.model.Course;
import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;

public interface CourseService {
    Course getCourseByCourseId(Long courseId);

    void saveCourse(CourseSharedDto courseDtoEvent);

    boolean isStudentEnrolled(Long studentId, Long courseId);

    boolean isInstructorOfCourse(Long instructorId, Long courseId);

}
