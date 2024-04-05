package com.StreamlineLearn.FeedbackManagment.service;


import com.StreamlineLearn.FeedbackManagment.model.Course;

public interface CourseService {
    Course getCourseByCourseId(Long id);
}
