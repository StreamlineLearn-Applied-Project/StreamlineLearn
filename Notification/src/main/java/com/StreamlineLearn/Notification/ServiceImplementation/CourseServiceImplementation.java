package com.StreamlineLearn.Notification.ServiceImplementation;



import com.StreamlineLearn.Notification.model.Course;
import com.StreamlineLearn.Notification.repository.CourseRepository;
import com.StreamlineLearn.Notification.service.CourseService;
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
