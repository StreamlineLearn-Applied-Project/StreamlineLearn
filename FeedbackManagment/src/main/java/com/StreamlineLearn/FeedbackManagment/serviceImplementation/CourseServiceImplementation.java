package com.StreamlineLearn.FeedbackManagment.serviceImplementation;


import com.StreamlineLearn.FeedbackManagment.model.Course;
import com.StreamlineLearn.FeedbackManagment.repository.CourseRepository;
import com.StreamlineLearn.FeedbackManagment.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImplementation implements CourseService {
    private final CourseRepository courseRepository;

    public CourseServiceImplementation(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course getCourseByCourseId(Long id) {
        return courseRepository.findByCourseId(id);
    }
}
