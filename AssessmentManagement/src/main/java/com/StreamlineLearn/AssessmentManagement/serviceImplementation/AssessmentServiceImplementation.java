package com.StreamlineLearn.AssessmentManagement.serviceImplementation;

import com.StreamlineLearn.AssessmentManagement.dto.AssessmentDto;
import com.StreamlineLearn.AssessmentManagement.exception.AssessmentCreationException;
import com.StreamlineLearn.AssessmentManagement.model.Assessment;
import com.StreamlineLearn.AssessmentManagement.model.Course;
import com.StreamlineLearn.AssessmentManagement.repository.AssessmentRepository;
import com.StreamlineLearn.AssessmentManagement.repository.CourseRepository;
import com.StreamlineLearn.AssessmentManagement.service.AssessmentService;
import com.StreamlineLearn.AssessmentManagement.service.CourseService;

import com.StreamlineLearn.AssessmentManagement.service.KafkaProducerService;
import com.StreamlineLearn.SharedModule.dto.CourseAssessmentDto;
import com.StreamlineLearn.SharedModule.jwtUtil.SharedJwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class AssessmentServiceImplementation implements AssessmentService {
    private final SharedJwtService sharedJwtService;
    private final CourseService courseService;
    private final AssessmentRepository assessmentRepository;
    private final CourseRepository courseRepository;
    private final KafkaProducerService kafkaProducerService;
    private static final int TOKEN_PREFIX_LENGTH = 7;
    private static final Logger logger = LoggerFactory.getLogger(AssessmentServiceImplementation.class);
    public AssessmentServiceImplementation(SharedJwtService sharedJwtService,
                                           CourseService courseService,
                                           AssessmentRepository assessmentRepository,
                                           CourseRepository courseRepository,
                                           KafkaProducerService kafkaProducerService) {

        this.sharedJwtService = sharedJwtService;
        this.courseService = courseService;
        this.assessmentRepository = assessmentRepository;
        this.courseRepository = courseRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public Assessment createAssessment(Long courseId, Assessment assessment, String authorizationHeader) {

        try {
                Course course = courseService.getCourseByCourseId(courseId);
                Long instructorId = sharedJwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

                // Check if the course exists and if the logged-in instructor owns the course
                if (course != null && course.getInstructor().getId().equals(instructorId)) {
                    assessment.setCourse(course);
                    assessmentRepository.save(assessment);

                    // Publish course assessment details
                    CourseAssessmentDto courseAssessmentDto = new CourseAssessmentDto(instructorId, courseId, assessment.getId());
                    kafkaProducerService.publishCourseAssessmentDetails(courseAssessmentDto);

                    return assessment;

                }else {
                    // Handle the case where the course doesn't exist, or the instructor doesn't own the course
                    throw new RuntimeException("Instructor is not authorized to create Assessment for this course");
                }

        } catch (Exception ex) {
            // Log the error and throw a custom exception to Assessment creation fails
            logger.error("An error occurred while creating Assessment", ex);
            // Rethrow the exception or throw a custom exception
            throw new AssessmentCreationException("Failed to create Assessment: " + ex.getMessage());
        }
    }

    @Override
    public Set<Assessment> getAssessmentsByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(null);
        return course.getAssessments();
    }

    @Override
    public Optional<AssessmentDto> getAssessmentById(Long courseId, Long assessmentId, String authorizationHeader) {
        String role = sharedJwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = sharedJwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            // Check if the owner of the course is the instructor
            if ("INSTRUCTOR".equals(role) && Objects.equals(course.getInstructor().getId(), roleId)) {
                Optional<Assessment> optionalAssessment = course.getAssessments()
                        .stream()
                        .filter(assessment -> assessment.getId().equals(assessmentId))
                        .findFirst();
                if (optionalAssessment.isPresent()) {
                    Assessment assessment = optionalAssessment.get();
                    return Optional.of(new AssessmentDto(assessment.getTitle(), assessment.getPercentage()));
                } else {
                    return Optional.empty(); // Assessment not found
                }
            }

            // Check if the role is a student
            if ("STUDENT".equals(role)) {
                RestTemplate restTemplate = new RestTemplate();
                String enrolmentApiUrl = "http://localhost:9090/courses/" + courseId + "/enrollments/check/" + roleId;
                String apiResponse = restTemplate.getForObject(enrolmentApiUrl, String.class);
                System.out.println("API Response: " + apiResponse);

                // Assuming the API response indicates whether the student has paid for the course
                if ("PAID".equals(apiResponse)) {
                    Optional<Assessment> optionalAssessment = course.getAssessments()
                            .stream()
                            .filter(assessment -> assessment.getId().equals(assessmentId))
                            .findFirst();
                    if (optionalAssessment.isPresent()) {
                        Assessment assessment = optionalAssessment.get();
                        return Optional.of(new AssessmentDto(assessment.getTitle(), assessment.getPercentage()));
                    } else {
                        return Optional.empty(); // Assessment not found
                    }
                } else {
                    return Optional.empty(); // Student has not paid for the course
                }
            }
        }

        return Optional.empty(); // Course not found, or unauthorized access
    }



    @Override
    public boolean updateAssessmentById(Long courseId, Long assessmentId, Assessment assessment, String authorizationHeader) {
        String role = sharedJwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = sharedJwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            // Check if the owner of the course is the instructor
            if ("INSTRUCTOR".equals(role) && Objects.equals(course.getInstructor().getId(), roleId)) {
                Optional<Assessment> optionalAssessment = course.getAssessments()
                        .stream()
                        .filter(a -> a.getId().equals(assessmentId))
                        .findFirst();
                if (optionalAssessment.isPresent()) {
                    Assessment existingAssessment = optionalAssessment.get();
                    existingAssessment.setTitle(assessment.getTitle());
                    existingAssessment.setPercentage(assessment.getPercentage());
                    assessmentRepository.save(existingAssessment);
                    return true;
                } else {
                    // Assessment not found in the course
                    return false;
                }
            } else {
                // Instructor is not authorized to update assessment for this course
                return false;
            }
        } else {
            // Course not found
            return false;
        }
    }

    @Override
    public boolean deleteAssessmentById(Long courseId, Long assessmentId, String authorizationHeader) {
        String role = sharedJwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = sharedJwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            // Check if the owner of the course is the instructor
            if ("INSTRUCTOR".equals(role) && Objects.equals(course.getInstructor().getId(), roleId)) {
                Optional<Assessment> optionalAssessment = course.getAssessments()
                        .stream()
                        .filter(a -> a.getId().equals(assessmentId))
                        .findFirst();
                if (optionalAssessment.isPresent()) {
                    // Remove the announcement from the course's list of announcements
                    Assessment assessmentToRemove = optionalAssessment.get();
                    course.getAssessments().remove(assessmentToRemove);

                    courseRepository.save(course); // Save the course to update the changes
                    assessmentRepository.deleteById(assessmentId);
                    return true;
                } else {
                    // Assessment not found in the course
                    return false;
                }
            } else {
                // Instructor is not authorized to delete assessment for this course
                return false;
            }
        } else {
            // Course not found
            return false;
        }
    }


}
