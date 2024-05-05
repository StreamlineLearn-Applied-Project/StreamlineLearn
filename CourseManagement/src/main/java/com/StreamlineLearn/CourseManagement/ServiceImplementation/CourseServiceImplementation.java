package com.StreamlineLearn.CourseManagement.ServiceImplementation;

import com.StreamlineLearn.CourseManagement.exception.CourseException;
import com.StreamlineLearn.CourseManagement.model.Course;
import com.StreamlineLearn.CourseManagement.model.Instructor;
import com.StreamlineLearn.CourseManagement.repository.CourseRepository;
import com.StreamlineLearn.CourseManagement.service.CourseService;
import com.StreamlineLearn.CourseManagement.service.InstructorService;
import com.StreamlineLearn.CourseManagement.service.KafkaProducerService;
import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.StreamlineLearn.SharedModule.service.JwtUserService;
import com.StreamlineLearn.SharedModule.sharedException.CourseNotFoundException;
import com.StreamlineLearn.SharedModule.sharedException.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
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
    private final JwtUserService jwtUserService;
    private final KafkaProducerService kafkaProducerService;
    @Value("${enrollment.check.url}")
    private String enrollmentCheckUrl;
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImplementation.class);


    public CourseServiceImplementation(CourseRepository courseRepository,
                                       InstructorService instructorService,
                                       JwtUserService jwtUserService,
                                       KafkaProducerService kafkaProducerService) {

        this.courseRepository = courseRepository;
        this.instructorService = instructorService;
        this.jwtUserService = jwtUserService;
        this.kafkaProducerService = kafkaProducerService;
    }

    private Instructor findInstructor(String authorizationHeader) {
        // Extract the instructor's ID from the JWT token and find the instructor
        UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

        return instructorService.findInstructorById(userSharedDto.getId());
    }

    @Override
    public Course createCourse(Course course, String authorizationHeader) {
        try {
            // Extract the instructor's ID from the JWT token and find the instructor
           Instructor instructor = findInstructor(authorizationHeader);

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
            throw new CourseException("Failed to create course: " + ex.getMessage());
        }
    }

    @Override
    public List<Course> getAllTheCourse() {
        try {
            return courseRepository.findAll();
        } catch (Exception ex) {
            logger.error("An error occurred while retrieving all courses", ex);
            throw new CourseException("Failed to retrieve all courses", ex);
        }
    }


    @Override
    public Set<Course> getAllInstructorCourse(String authorizationHeader) {
        try {
            // Extract the instructor's ID from the JWT token and find the instructor
            Instructor instructor = findInstructor(authorizationHeader);

            if (instructor == null) {
                throw new IllegalArgumentException("Instructor not found");
            }

            // Returning the courses associated with the instructor
            return instructor.getCourses();
        } catch (Exception ex) {
            logger.error("An error occurred while retrieving courses for the instructor", ex);
            throw new CourseException("Failed to retrieve courses for the instructor", ex);
        }
    }


    @Override
    public Optional<Course> getCourseById(Long id, String authorizationHeader) {
        try {
            Optional<Course> course = courseRepository.findById(id);

            if (course.isEmpty()) {
                return Optional.empty();
            }

            // If the token is null, return course details without authentication
            if (authorizationHeader == null) {
                return course;
            }

            // Extract the instructor's ID from the JWT token and find the instructor
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

            if ("INSTRUCTOR".equals(userSharedDto.getRole()) &&
                    Objects.equals(course.get().getInstructor().getId(), userSharedDto.getId())) {

                return Optional.of(new Course(course.get().getId(), course.get().getCourseName(),
                        course.get().getDescription(), course.get().getPrice(),
                        course.get().getCreationDate(), course.get().getLastUpdated(),
                        course.get().getInstructor()));
            }

            if ("STUDENT".equals(userSharedDto.getRole())) {

                RestTemplate restTemplate = new RestTemplate();
                String enrolmentApiUrl = enrollmentCheckUrl
                        .replace("{id}", String.valueOf(id))
                        .replace("{userId}", String.valueOf(userSharedDto.getId()));

                String apiResponse = restTemplate.getForObject(enrolmentApiUrl, String.class);

                logger.info("API Response: {}", apiResponse);

                if ("PAID".equals(apiResponse)) {
                    return Optional.of(new Course(course.get().getId(), course.get().getCourseName(),
                            course.get().getDescription(), course.get().getCreationDate(),
                            course.get().getLastUpdated(), course.get().getInstructor()));
                } else {
                    return Optional.of(new Course(course.get().getId(), course.get().getCourseName(),
                            course.get().getDescription(), course.get().getPrice(),
                            course.get().getCreationDate(), course.get().getLastUpdated(),
                            course.get().getInstructor()));

                }
            }
            return Optional.empty();
        } catch (Exception ex) {
            logger.error("An error occurred while retrieving course with ID: {}", id, ex);
            throw new CourseException("Failed to retrieve course with ID: " + id, ex);
        }
    }




    @Override
    public boolean updateCourseById(Long id, Course course, String authorizationHeader) {
        try {
            Optional<Course> courseOptional = courseRepository.findById(id);
            if (courseOptional.isEmpty()) {
                throw new CourseNotFoundException("Course not found");
            }
            // Extract the instructor's ID from the JWT token and find the instructor
            Instructor instructor = findInstructor(authorizationHeader);
            if (!Objects.equals(courseOptional.get().getInstructor().getId(), instructor.getId())) {
                throw new UnauthorizedException("You are not authorized to update this course");
            }
            Course updateCourse = courseOptional.get();
            updateCourse.setCourseName(course.getCourseName());
            updateCourse.setDescription(course.getDescription());
            updateCourse.setPrice(course.getPrice());
            courseRepository.save(updateCourse);
            return true;
        } catch (CourseNotFoundException ex) {
            logger.error("Course not found with ID: {}", id, ex);
            throw ex;
        } catch (UnauthorizedException ex) {
            logger.error("Unauthorized access for updating course with ID: {}", id, ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("An error occurred while updating course with ID: {}", id, ex);
            throw new CourseException("Failed to update course with ID: " + id, ex);
        }
    }



    @Override
    public boolean deleteCourseById(Long id, String authorizationHeader) {
        try {
            Optional<Course> courseOptional = courseRepository.findById(id);
            if (courseOptional.isEmpty()) {
                throw new CourseNotFoundException("Course not found");
            }
            // Extract the instructor's ID from the JWT token and find the instructor
            Instructor instructor = findInstructor(authorizationHeader);
            if (!Objects.equals(courseOptional.get().getInstructor().getId(), instructor.getId())) {
                throw new UnauthorizedException("You are not authorized to delete this course");
            }
            courseRepository.deleteById(id);
            return true;
        } catch (CourseNotFoundException ex) {
            logger.error("Course not found with ID: {}", id, ex);
            throw ex;
        } catch (UnauthorizedException ex) {
            logger.error("Unauthorized access for deleting course with ID: {}", id, ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("An error occurred while deleting course with ID: {}", id, ex);
            throw new CourseException("Failed to delete course with ID: " + id, ex);
        }
    }


}
