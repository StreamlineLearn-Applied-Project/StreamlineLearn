package com.StreamlineLearn.CourseManagement.ServiceImplementation;

import com.StreamlineLearn.CourseManagement.dto.CourseDTO;
import com.StreamlineLearn.CourseManagement.exception.CourseCreationException;
import com.StreamlineLearn.CourseManagement.model.Course;
import com.StreamlineLearn.CourseManagement.model.Instructor;
import com.StreamlineLearn.CourseManagement.repository.CourseRepository;
import com.StreamlineLearn.CourseManagement.service.CourseService;
import com.StreamlineLearn.CourseManagement.service.InstructorService;
import com.StreamlineLearn.CourseManagement.service.KafkaProducerService;
import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;
import com.StreamlineLearn.SharedModule.jwtUtil.SharedJwtService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseServiceImplementation implements CourseService {
    private final CourseRepository courseRepository;
    private final InstructorService instructorService;
    private final SharedJwtService sharedJwtService;
    private final KafkaProducerService kafkaProducerService;
    private static final int TOKEN_PREFIX_LENGTH = 7;
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImplementation.class);


    public CourseServiceImplementation(CourseRepository courseRepository,
                                       InstructorService instructorService,
                                       SharedJwtService sharedJwtService,
                                       KafkaProducerService kafkaProducerService) {

        this.courseRepository = courseRepository;
        this.instructorService = instructorService;
        this.sharedJwtService = sharedJwtService;

        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public Course createCourse(Course course, String token) {
        try {
            // Extract the instructor's ID from the JWT token and find the instructor
            Instructor instructor = instructorService.findInstructorById(sharedJwtService
                .extractRoleId(token.substring(TOKEN_PREFIX_LENGTH)));

            // If no instructor is found, throw an exception
            if (instructor == null) {
                throw new IllegalArgumentException("Instructor not found");
            }

            // Set the instructor of the course and save the course to the repository
            course.setInstructor(instructor);
            courseRepository.save(course);

            // Prepare a DTO to share course details via Kafka
            CourseSharedDto courseSharedDto = new CourseSharedDto();
            courseSharedDto.setId(course.getId());
            courseSharedDto.setCourseName(course.getCourseName());
            courseSharedDto.setInstructorId(course.getInstructor().getId());

            // Publish the course details to Kafka
            kafkaProducerService.publishCourseDetails(courseSharedDto);

            // Return the saved course object
            return course;
        }catch (Exception ex){
            // Log the error and throw a custom exception of course creation fails
            logger.error("An error occurred while creating Course", ex);
            // Rethrow the exception or throw a custom exception
            throw new CourseCreationException("Failed to create course: " + ex.getMessage());
        }
    }

    @Override
    public List<Course> getAllTheCourse() {
        return courseRepository.findAll();
    }

    @Override
    public Set<Course> getAllInstructorCourse(String authorizationHeader) {
        Instructor instructor = instructorService.findInstructorById(sharedJwtService.
                extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH)));

        if (instructor == null) {
            throw new IllegalArgumentException("Instructor not found");
        }

        // Returning the courses associated with the instructor
        return instructor.getCourses();

    }

    @Override
    public Optional<CourseDTO> getCourseById(Long id, String token) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) {
            return Optional.empty();
        }

        // If token is null, return course details without authentication
        if (token == null) {
            return Optional.of(new CourseDTO(course.get().getCourseName(),
                    course.get().getDescription(),
                    course.get().getPrice()));
        }

        String role = sharedJwtService.extractRole(token.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = sharedJwtService.extractRoleId(token.substring(TOKEN_PREFIX_LENGTH));

        if ("INSTRUCTOR".equals(role) && Objects.equals(course.get().getInstructor().getId(), roleId)) {
            return Optional.of(new CourseDTO(course.get().getCourseName(),
                    course.get().getDescription(),
                    course.get().getPrice()));
        }

        if ("STUDENT".equals(role)) {
            RestTemplate restTemplate = new RestTemplate();
            String enrolmentApiUrl = "http://localhost:9090/courses/" + id + "/enrollments/check/" + roleId;
            String apiResponse = restTemplate.getForObject(enrolmentApiUrl, String.class);
            System.out.println("API Response: " + apiResponse);

            if ("PAID".equals(apiResponse)) {
                return Optional.of(new CourseDTO(course.get().getCourseName(),
                        course.get().getDescription()));
            } else {
                return Optional.of(new CourseDTO(course.get().getCourseName(),
                        course.get().getDescription(),
                        course.get().getPrice()));

            }
        }
        return Optional.empty();
    }



    @Override
    public boolean updateCourseById(Long id,Course course, String token) {
        Optional<Course> courseOptional = courseRepository.findById(id);

        Long instructorId = sharedJwtService.extractRoleId(token.substring(TOKEN_PREFIX_LENGTH));

        if(courseOptional.isPresent()){
            if (Objects.equals(courseOptional.get().getInstructor().getId(), instructorId)){
                Course updateCourse = courseOptional.get();
                updateCourse.setCourseName(course.getCourseName());
                updateCourse.setDescription(course.getDescription());
                updateCourse.setPrice(course.getPrice());

                courseRepository.save(updateCourse);
                return true;
            }
           return false;
        }
        return false;
    }

    @Override
    public boolean deleteCourseById(Long id, String token) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        Long instructorId = sharedJwtService.extractRoleId(token.substring(TOKEN_PREFIX_LENGTH));

        if(courseOptional.isPresent()){
            if (Objects.equals(courseOptional.get().getInstructor().getId(), instructorId)) {
                courseRepository.deleteById(id);
                return true;
            }
            return false;
        }
        return false;
    }


}
