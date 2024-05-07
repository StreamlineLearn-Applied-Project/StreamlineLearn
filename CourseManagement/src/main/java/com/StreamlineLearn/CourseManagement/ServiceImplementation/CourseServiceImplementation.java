package com.StreamlineLearn.CourseManagement.ServiceImplementation;

import com.StreamlineLearn.CourseManagement.exception.CourseException;
import com.StreamlineLearn.CourseManagement.model.Course;
import com.StreamlineLearn.CourseManagement.model.CourseMedia;
import com.StreamlineLearn.CourseManagement.model.Instructor;
import com.StreamlineLearn.CourseManagement.repository.CourseMediaRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseServiceImplementation implements CourseService {
    private final CourseRepository courseRepository;
    private final InstructorService instructorService;
    private final JwtUserService jwtUserService;
    private final CourseMediaRepository courseMediaRepository;
    private final KafkaProducerService kafkaProducerService;
    @Value("${enrollment.check.url}")
    private String enrollmentCheckUrl;
    @Value("${app.folder-path}")
    private String FOLDER_PATH;
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImplementation.class);


    public CourseServiceImplementation(CourseRepository courseRepository,
                                       InstructorService instructorService,
                                       JwtUserService jwtUserService,
                                       CourseMediaRepository courseMediaRepository,
                                       KafkaProducerService kafkaProducerService) {

        this.courseRepository = courseRepository;
        this.instructorService = instructorService;
        this.jwtUserService = jwtUserService;
        this.courseMediaRepository = courseMediaRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    private Instructor findInstructor(String authorizationHeader) {
        // Extract the instructor's ID from the JWT token and find the instructor
        UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

        return instructorService.findInstructorById(userSharedDto.getId());
    }

    @Override
    public Course createCourse(Course course, MultipartFile file, String authorizationHeader) {
        try {
            String mediaFilePath = FOLDER_PATH + file.getOriginalFilename();
            // Extract the instructor's ID from the JWT token and find the instructor
           Instructor instructor = findInstructor(authorizationHeader);

            // If no instructor is found, throw an exception
            if (instructor == null) {
                throw new IllegalArgumentException("Instructor not found");
            }

            // Set the instructor of the course and save the course to the repository
            course.setInstructor(instructor);
            courseRepository.save(course);

            // Save the CourseMedia entity after the Content entity is saved
            CourseMedia courseMedia = CourseMedia.builder()
                    .mediaName(file.getOriginalFilename())
                    .type(file.getContentType())
                    .mediaFilePath(mediaFilePath)
                    .course(course) // Set the saved course entity
                    .build();
            courseMedia = courseMediaRepository.save(courseMedia);

            // Set the CourseMedia entity in the Content entity
            course.setCourseMedia(courseMedia);
            // Update the Course entity to reflect the relationship
            course = courseRepository.save(course);

            // Transfer the file
            file.transferTo(new File(mediaFilePath));


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
    public Optional<Course> getCourseById(Long courseId, String authorizationHeader) {
        try {
            Optional<Course> course = courseRepository.findById(courseId);

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

                return course;
            }

            if ("STUDENT".equals(userSharedDto.getRole())) {

                RestTemplate restTemplate = new RestTemplate();
                String enrolmentApiUrl = enrollmentCheckUrl
                        .replace("{id}", String.valueOf(courseId))
                        .replace("{userId}", String.valueOf(userSharedDto.getId()));

                String apiResponse = restTemplate.getForObject(enrolmentApiUrl, String.class);

                logger.info("API Response: {}", apiResponse);

                if ("PAID".equals(apiResponse)) {
                    return Optional.of(new Course(course.get().getId(), course.get().getCourseName(),
                            course.get().getDescription(), course.get().getCreationDate(),
                            course.get().getLastUpdated(), course.get().getInstructor(),
                            course.get().getCourseMedia()));
                } else {
                    return course;

                }
            }
            return Optional.empty();
        } catch (Exception ex) {
            logger.error("An error occurred while retrieving course with ID: {}", courseId, ex);
            throw new CourseException("Failed to retrieve course with ID: " + courseId, ex);
        }
    }

    @Override
    public byte[] getCourseMedia(Long courseId, String fileName) throws IOException {
        try {
            // Check if the course exists
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (courseOptional.isEmpty()) {
                throw new CourseNotFoundException("Course not found with ID: " + courseId);
            }

            // Query the CourseMedia repository based on courseId and fileName
            Optional<CourseMedia> courseMediaOptional = courseMediaRepository.findByMediaName(fileName);

            if (courseMediaOptional.isPresent()) {
                String mediaFilePath = courseMediaOptional.get().getMediaFilePath();
                return Files.readAllBytes(new File(mediaFilePath).toPath());
            } else {
                throw new FileNotFoundException("Media file not found: " + fileName);
            }
        } catch (IOException ex) {
            // Log IO exceptions
            logger.error("Error occurred while reading media file", ex);
            throw ex;
        } catch (CourseNotFoundException ex) {
            // Log and rethrow CourseNotFoundException
            logger.error(ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic CourseException
            logger.error("An unexpected error occurred while getting course media", ex);
            throw new CourseException("Failed to get course media: " + ex.getMessage(), ex);
        }
    }


    @Override
    public boolean updateCourseById(Long courseId, Course course, MultipartFile file, String authorizationHeader) {
        try {
            Optional<Course> courseOptional = courseRepository.findById(courseId);
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

            // Check if a new file is uploaded
            if (file != null && !file.isEmpty()) {
                String mediaFilePath = FOLDER_PATH + file.getOriginalFilename();

                // Delete the previous file if it exists
                if (updateCourse.getCourseMedia() != null) {
                    String previousFilePath = updateCourse.getCourseMedia().getMediaFilePath();
                    File previousFile = new File(previousFilePath);
                    if (previousFile.exists()) {
                        if (!previousFile.delete()) {
                            logger.warn("Failed to delete previous file: {}", previousFilePath);
                        }
                    }
                }

                // Save the new file
                file.transferTo(new File(mediaFilePath));

                // Update the CourseMedia entity if it exists
                if (updateCourse.getCourseMedia() != null) {
                    CourseMedia existingCourseMedia = updateCourse.getCourseMedia();
                    existingCourseMedia.setMediaName(file.getOriginalFilename());
                    existingCourseMedia.setType(file.getContentType());
                    existingCourseMedia.setMediaFilePath(mediaFilePath);
                    // Save the updated CourseMedia entity
                    courseMediaRepository.save(existingCourseMedia);
                } else {
                    // If no CourseMedia exists, create a new one
                    CourseMedia courseMedia = CourseMedia.builder()
                            .mediaName(file.getOriginalFilename())
                            .type(file.getContentType())
                            .mediaFilePath(mediaFilePath)
                            .course(updateCourse)
                            .build();
                    // Save the new CourseMedia entity
                    courseMediaRepository.save(courseMedia);
                    // Set the CourseMedia entity in the Course entity
                    updateCourse.setCourseMedia(courseMedia);
                }
            }

            courseRepository.save(updateCourse);
            return true;
        } catch (CourseNotFoundException ex) {
            logger.error("Course not found with ID: {}", courseId, ex);
            throw ex;
        } catch (UnauthorizedException ex) {
            logger.error("Unauthorized access for updating course with ID: {}", courseId, ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("An error occurred while updating course with ID: {}", courseId, ex);
            throw new CourseException("Failed to update course with ID: " + courseId, ex);
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
