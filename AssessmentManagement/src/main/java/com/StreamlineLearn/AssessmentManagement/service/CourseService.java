package com.StreamlineLearn.AssessmentManagement.service;

import com.StreamlineLearn.AssessmentManagement.external.CourseExternal;
import com.StreamlineLearn.AssessmentManagement.model.Course;

public interface CourseService {
    Course createCourse(CourseExternal course);
    Course getCourseByCourseId(Long id);
}
