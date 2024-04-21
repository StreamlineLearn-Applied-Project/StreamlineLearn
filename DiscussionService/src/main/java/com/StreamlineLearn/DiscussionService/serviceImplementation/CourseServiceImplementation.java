package com.StreamlineLearn.DiscussionService.serviceImplementation;



import com.StreamlineLearn.DiscussionService.model.Course;
import com.StreamlineLearn.DiscussionService.repository.CourseRepository;
import com.StreamlineLearn.DiscussionService.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImplementation implements CourseService {
    private final CourseRepository courseRepository;

    public CourseServiceImplementation(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course getCourseByCourseId(Long id) {
        return courseRepository.findById(id).orElseThrow(null);
    }
}
