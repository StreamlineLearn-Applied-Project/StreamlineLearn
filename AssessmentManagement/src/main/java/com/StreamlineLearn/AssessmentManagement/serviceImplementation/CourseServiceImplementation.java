package com.StreamlineLearn.AssessmentManagement.serviceImplementation;


import com.StreamlineLearn.AssessmentManagement.model.Course;
import com.StreamlineLearn.AssessmentManagement.repository.CourseRepository;
import com.StreamlineLearn.AssessmentManagement.service.CourseService;
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
