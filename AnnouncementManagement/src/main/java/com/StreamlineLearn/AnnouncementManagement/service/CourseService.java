package com.StreamlineLearn.AnnouncementManagement.service;

import com.StreamlineLearn.AnnouncementManagement.model.Course;
import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;

public interface CourseService {
    Course getCourseByCourseId(Long id);
    public void saveCourse(CourseSharedDto courseDtoEvent);
}
