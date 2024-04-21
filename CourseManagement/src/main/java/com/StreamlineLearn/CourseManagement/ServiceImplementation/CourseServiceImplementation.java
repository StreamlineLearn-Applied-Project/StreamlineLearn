package com.StreamlineLearn.CourseManagement.ServiceImplementation;

import com.StreamlineLearn.CourseManagement.jwtUtil.JwtService;
import com.StreamlineLearn.CourseManagement.model.Course;
import com.StreamlineLearn.CourseManagement.model.Instructor;
import com.StreamlineLearn.CourseManagement.repository.CourseRepository;
import com.StreamlineLearn.CourseManagement.repository.InstructorRepository;
import com.StreamlineLearn.CourseManagement.service.CourseService;
import com.StreamlineLearn.CourseManagement.service.InstructorService;
import com.StreamlineLearn.CourseManagement.service.KafkaProducerService;
import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImplementation implements CourseService {
    private final CourseRepository courseRepository;
    private final InstructorService instructorService;
    private final JwtService jwtService;
    private final KafkaProducerService kafkaProducerService;


    public CourseServiceImplementation(CourseRepository courseRepository,
                                       InstructorService instructorService,
                                       JwtService jwtService,
                                       KafkaProducerService kafkaProducerService) {

        this.courseRepository = courseRepository;
        this.instructorService = instructorService;
        this.jwtService = jwtService;

        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public void createCourse(Course course, String token) {
        Instructor instructor = instructorService
                .findInstructorById(jwtService
                        .extractRoleId(token
                                .substring(7)));

        if(instructor == null) {
            throw new IllegalArgumentException("Instructor not found");

        } else {
            course.setInstructor(instructor);
            courseRepository.save(course);

            CourseSharedDto courseSharedDto = new CourseSharedDto();
            courseSharedDto.setId(course.getId());
            courseSharedDto.setCourseName(course.getCourseName());
            courseSharedDto.setInstructorId(course.getInstructor().getId());

            kafkaProducerService.publishCourseDetails(courseSharedDto);
        }
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
        Optional<Course> courseOptional = courseRepository.findById(id); // repeating same block of code
        if(courseOptional.isPresent()){
           Course updateCourse = courseOptional.get();
           updateCourse.setCourseName(course.getCourseName());
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
