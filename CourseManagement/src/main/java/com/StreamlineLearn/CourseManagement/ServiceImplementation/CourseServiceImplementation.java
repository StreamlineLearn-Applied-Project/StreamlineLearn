package com.StreamlineLearn.CourseManagement.ServiceImplementation;

import com.StreamlineLearn.CourseManagement.model.Course;
import com.StreamlineLearn.CourseManagement.repository.CourseRepository;
import com.StreamlineLearn.CourseManagement.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImplementation implements CourseService {
    private final CourseRepository courseRepository;

    public CourseServiceImplementation(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void createCourse(Course course) {
        courseRepository.save(course);

    }

    @Override
    public List<Course> getAllTheCourse() {

        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateCourseById(Long id,Course course) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if(courseOptional.isPresent()){
           Course updateCourse = courseOptional.get();
           updateCourse.setTitle(course.getTitle());
           updateCourse.setDescription(course.getDescription());
           updateCourse.setPrice(course.getPrice());

           courseRepository.save(updateCourse);

           return true;
        }
        return false;
    }

    @Override
    public boolean deleteCourseById(Long id) {
        if(courseRepository.existsById(id)){
            courseRepository.deleteById(id);

            return true;
        }
        return false;
    }
}
