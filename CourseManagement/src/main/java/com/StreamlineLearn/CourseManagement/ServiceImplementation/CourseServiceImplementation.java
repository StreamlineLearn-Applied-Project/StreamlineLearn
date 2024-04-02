package com.StreamlineLearn.CourseManagement.ServiceImplementation;

import com.StreamlineLearn.CourseManagement.model.Course;
import com.StreamlineLearn.CourseManagement.model.Instructor;
import com.StreamlineLearn.CourseManagement.repository.CourseRepository;
import com.StreamlineLearn.CourseManagement.service.CourseService;
import com.StreamlineLearn.CourseManagement.service.InstructorService;
import com.StreamlineLearn.CourseManagement.utility.JwtService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImplementation implements CourseService {
    private final CourseRepository courseRepository;
    private final InstructorService instructorService;

    public CourseServiceImplementation(CourseRepository courseRepository,
                                       InstructorService instructorService) {

        this.courseRepository = courseRepository;
        this.instructorService = instructorService;
    }

    @Override
    public void createCourse(Course course, String token) {
       Instructor instructor = instructorService.saveInstructor(token);

       course.setInstructor(instructor);

        courseRepository.save(course);

    }

    @Override
    public void enrollStudent() {

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
