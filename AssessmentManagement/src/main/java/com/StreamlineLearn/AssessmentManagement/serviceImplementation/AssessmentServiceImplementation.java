package com.StreamlineLearn.AssessmentManagement.serviceImplementation;

import com.StreamlineLearn.AssessmentManagement.dto.AssessmentDto;
import com.StreamlineLearn.AssessmentManagement.exception.AssessmentException;
import com.StreamlineLearn.AssessmentManagement.model.Assessment;
import com.StreamlineLearn.AssessmentManagement.model.Course;
import com.StreamlineLearn.AssessmentManagement.repository.AssessmentRepository;
import com.StreamlineLearn.AssessmentManagement.repository.CourseRepository;
import com.StreamlineLearn.AssessmentManagement.service.AssessmentService;
import com.StreamlineLearn.AssessmentManagement.service.CourseService;

import com.StreamlineLearn.AssessmentManagement.service.KafkaProducerService;
import com.StreamlineLearn.SharedModule.dto.CourseAssessmentDto;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.StreamlineLearn.SharedModule.jwtUtil.SharedJwtService;
import com.StreamlineLearn.SharedModule.service.EnrollmentService;
import com.StreamlineLearn.SharedModule.service.JwtUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class AssessmentServiceImplementation implements AssessmentService {
    private final JwtUserService jwtUserService;
    private final CourseService courseService;
    private final AssessmentRepository assessmentRepository;
    private final CourseRepository courseRepository;
    private final KafkaProducerService kafkaProducerService;
    private static final int TOKEN_PREFIX_LENGTH = 7;

    private final EnrollmentService enrollmentService;
    private static final Logger logger = LoggerFactory.getLogger(AssessmentServiceImplementation.class);
    public AssessmentServiceImplementation(JwtUserService jwtUserService,
                                           CourseService courseService,
                                           AssessmentRepository assessmentRepository,
                                           CourseRepository courseRepository,
                                           KafkaProducerService kafkaProducerService,
                                           EnrollmentService enrollmentService) {

        this.jwtUserService = jwtUserService;
        this.courseService = courseService;
        this.assessmentRepository = assessmentRepository;
        this.courseRepository = courseRepository;
        this.kafkaProducerService = kafkaProducerService;
        this.enrollmentService = enrollmentService;
    }

    private boolean isAuthorizedAsInstructorOrStudent(String role, Long roleId, Long courseId) {
        if ("INSTRUCTOR".equals(role)) {
            return courseService.isInstructorOfCourse(roleId, courseId);
        } else if ("STUDENT".equals(role)) {
            return enrollmentService.hasStudentPaidForCourse(roleId, courseId);
        }
        return false; // Any other role is not authorized
    }

    // Method to check if a user is authorized as an instructor
    private boolean isAuthorizedAsInstructor(String role, Long instructorId, Long courseId) {
        return "INSTRUCTOR".equals(role) && courseService.isInstructorOfCourse(instructorId, courseId);
    }

    // Method to create an assessment
    @Override
    public Assessment createAssessment(Long courseId, Assessment assessment, String authorizationHeader) {
        try {
                Course course = courseService.getCourseByCourseId(courseId);
                UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

                // Check if the course exists and if the logged-in instructor owns the course
                if (course != null && course.getInstructor().getId().equals(userSharedDto.getId())) {
                    assessment.setCourse(course);
                    assessmentRepository.save(assessment);
                    // Publish course assessment details
                    CourseAssessmentDto courseAssessmentDto = new CourseAssessmentDto(userSharedDto.getId(),
                            courseId, assessment.getId());
                    kafkaProducerService.publishCourseAssessmentDetails(courseAssessmentDto);

                    return assessment;

                }else {
                    // Handle the case where the course doesn't exist, or the instructor doesn't own the course
                    throw new AssessmentException("Instructor is not authorized to create Assessment for this course");
                }

        } catch (Exception ex) {
            // Log the error and throw a custom exception to Assessment creation fails
            logger.error("An error occurred while creating Assessment", ex);
            // Rethrow the exception or throw a custom exception
            throw new AssessmentException("Failed to create Assessment: " + ex.getMessage());
        }
    }

    // Method to get Assessments by course ID
    @Override
    public Set<Assessment> getAssessmentsByCourseId(Long courseId,String authorizationHeader) {
        try{
            Course course = courseRepository.findById(courseId).orElseThrow(null);
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

            return (course != null && isAuthorizedAsInstructorOrStudent(userSharedDto.getRole(),
                    userSharedDto.getId(), courseId)) ?
                    course.getAssessments(): null;

        }catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic AnnouncementException
            logger.error("An unexpected error occurred while getting Assessment", ex);
            throw new AssessmentException("Failed to get Assessment: " + ex.getMessage(), ex);
        }
    }

    // Method to get an announcement by ID
    @Override
    public Optional<Assessment> getAssessmentById(Long courseId, Long assessmentId, String authorizationHeader) {
        try{
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);
            Course course = courseService.getCourseByCourseId(courseId);

            if (course != null && isAuthorizedAsInstructorOrStudent(userSharedDto.getRole(),
                    userSharedDto.getId(), courseId)) {
                return course.getAssessments().stream()
                        .filter(assessment -> assessment.getId().equals(assessmentId))
                        .map(assessment -> new Assessment(assessment.getId(),
                                assessment.getTitle(),
                                assessment.getPercentage(),
                                assessment.getCourse()))
                        .findFirst();
            }
            return Optional.empty();
        } catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic AssessmentException
            logger.error("An unexpected error occurred while getting assessment by ID", ex);
            throw new AssessmentException("Failed to get assessment by ID: " + ex.getMessage(), ex);
        }

    }

    // Method to update an Assessment by ID
    @Override
    public boolean updateAssessmentById(Long courseId, Long assessmentId, Assessment assessment, String authorizationHeader) {
        try {
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);
            Course course = courseService.getCourseByCourseId(courseId);

            if (course != null && isAuthorizedAsInstructor(userSharedDto.getRole(),
                    userSharedDto.getId(), courseId))  {
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
                    }
            }
            // Assessment not found in the course
            return false;

        }  catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic AssessmentException
            logger.error("An unexpected error occurred while updating Assessment by ID", ex);
            throw new AssessmentException("Failed to update Assessment by ID: " + ex.getMessage(), ex);
        }
    }

    @Override
    public boolean deleteAssessmentById(Long courseId, Long assessmentId, String authorizationHeader) {
        try {
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);
            Course course = courseService.getCourseByCourseId(courseId);

            if (course != null && isAuthorizedAsInstructor(userSharedDto.getRole(), userSharedDto.getId(), courseId)) {
                    Optional<Assessment> optionalAssessment = course.getAssessments()
                            .stream()
                            .filter(a -> a.getId().equals(assessmentId))
                            .findFirst();
                    if (optionalAssessment.isPresent()) {
                        // Remove the assessment from the course's list of assessments
                        Assessment assessmentToRemove = optionalAssessment.get();
                        course.getAssessments().remove(assessmentToRemove);

                        courseService.saveCourse(course); // Save the course to update the changes
                        assessmentRepository.deleteById(assessmentId);
                        return true;
                    }
                        // Assessment not found in the course
                        return false;
            }
            // Instructor is not authorized to delete assessment for this course
            return false;

        } catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic AnnouncementException
            logger.error("An unexpected error occurred while deleting announcement by ID", ex);
            throw new AssessmentException("Failed to delete announcement by ID: " + ex.getMessage(), ex);
        }
    }
}
