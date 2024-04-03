package com.StreamlineLearn.AssessmentManagement.serviceImplementation;

import com.StreamlineLearn.AssessmentManagement.external.CourseExternal;
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

    public Course createCourse(CourseExternal course) {
        Course newCourse = new Course();

        newCourse.setCourseId(course.getId());

        courseRepository.save(newCourse);

        return newCourse;
    }
    @Override
    public Course getCourseByCourseId(Long id) {
        return courseRepository.findByCourseId(id);
    }
}
