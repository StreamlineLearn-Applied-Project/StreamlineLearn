package com.StreamlineLearn.AssessmentManagement.serviceImplementation;

import com.StreamlineLearn.AssessmentManagement.exception.AssessmentException;
import com.StreamlineLearn.AssessmentManagement.model.Assessment;
import com.StreamlineLearn.AssessmentManagement.model.AssessmentMedia;
import com.StreamlineLearn.AssessmentManagement.model.Course;
import com.StreamlineLearn.AssessmentManagement.repository.AssessmentMediaRepository;
import com.StreamlineLearn.AssessmentManagement.repository.AssessmentRepository;
import com.StreamlineLearn.AssessmentManagement.repository.CourseRepository;
import com.StreamlineLearn.AssessmentManagement.service.AssessmentService;
import com.StreamlineLearn.AssessmentManagement.service.CourseService;

import com.StreamlineLearn.AssessmentManagement.service.KafkaProducerService;
import com.StreamlineLearn.SharedModule.dto.CourseAssessmentDto;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.StreamlineLearn.SharedModule.service.EnrollmentService;
import com.StreamlineLearn.SharedModule.service.JwtUserService;
import com.StreamlineLearn.SharedModule.sharedException.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Set;

@Service
public class AssessmentServiceImplementation implements AssessmentService {
    private final JwtUserService jwtUserService;
    private final CourseService courseService;
    private final AssessmentRepository assessmentRepository;
    private final CourseRepository courseRepository;
    private final KafkaProducerService kafkaProducerService;
    private final AssessmentMediaRepository assessmentMediaRepository;
    @Value("${app.folder-path}")
    private String FOLDER_PATH;

    private final EnrollmentService enrollmentService;
    private static final Logger logger = LoggerFactory.getLogger(AssessmentServiceImplementation.class);
    public AssessmentServiceImplementation(JwtUserService jwtUserService,
                                           CourseService courseService,
                                           AssessmentRepository assessmentRepository,
                                           CourseRepository courseRepository,
                                           KafkaProducerService kafkaProducerService,
                                           AssessmentMediaRepository assessmentMediaRepository,
                                           EnrollmentService enrollmentService) {

        this.jwtUserService = jwtUserService;
        this.courseService = courseService;
        this.assessmentRepository = assessmentRepository;
        this.courseRepository = courseRepository;
        this.kafkaProducerService = kafkaProducerService;
        this.assessmentMediaRepository = assessmentMediaRepository;
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
    public Assessment createAssessment(Long courseId, Assessment assessment, MultipartFile file, String authorizationHeader) {
        try {
                String mediaFilePath = FOLDER_PATH + file.getOriginalFilename();
                Course course = courseService.getCourseByCourseId(courseId);
                UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

                // Check if the course exists and if the logged-in instructor owns the course
                if (course != null && course.getInstructor().getId().equals(userSharedDto.getId())) {
                    assessment.setCourse(course);
                    // Save the Content entity first
                    Assessment savedAssessment = assessmentRepository.save(assessment);

                    // Save the AssessmentMedia entity after the Assessment entity is saved
                    AssessmentMedia assessmentMedia = AssessmentMedia.builder()
                            .mediaName(file.getOriginalFilename())
                            .type(file.getContentType())
                            .mediaFilePath(mediaFilePath)
                            .assessment(savedAssessment)
                            .build();
                    assessmentMedia = assessmentMediaRepository.save(assessmentMedia);

                    // Set the ContentMedia entity in the Content entity
                    savedAssessment.setAssessmentMedia(assessmentMedia);
                    // Update the Content entity to reflect the relationship
                    savedAssessment = assessmentRepository.save(savedAssessment);

                    // Transfer the file
                    file.transferTo(new File(mediaFilePath));

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
                                assessment.getAssessmentTitle(),
                                assessment.getAssessmentDescription(),
                                assessment.getPercentage(),
                                assessment.getCreationDate(),
                                assessment.getLastUpdated(),
                                assessment.getCourse(),
                                assessment.getAssessmentMedia()))
                        .findFirst();
            }
            return Optional.empty();
        } catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic AssessmentException
            logger.error("An unexpected error occurred while getting assessment by ID", ex);
            throw new AssessmentException("Failed to get assessment by ID: " + ex.getMessage(), ex);
        }

    }

    // Method to retrieve AssessmentMedia
    @Override
    public byte[] getAssessmentMedia(Long courseId, String fileName, String authorizationHeader) throws IOException {
        try {
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);
            Course course = courseService.getCourseByCourseId(courseId);

            if (course != null && isAuthorizedAsInstructorOrStudent(userSharedDto.getRole(), userSharedDto.getId(), courseId)) {
                Optional<AssessmentMedia> assessmentMedia = assessmentMediaRepository.findByMediaName(fileName);
                if (assessmentMedia.isPresent()) {
                    String mediaFilePath = assessmentMedia.get().getMediaFilePath();
                    return Files.readAllBytes(new File(mediaFilePath).toPath());
                } else {
                    throw new FileNotFoundException("Media file not found: " + fileName);
                }
            } else {
                throw new UnauthorizedException("User is not authorized to access media for this course");
            }
        } catch (IOException ex) {
            // Log IO exceptions
            logger.error("Error occurred while reading media file", ex);
            throw ex;
        } catch (UnauthorizedException ex) {
            // Log and rethrow UnauthorizedException
            logger.error(ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic AssessmentException
            logger.error("An unexpected error occurred while getting assessment media", ex);
            throw new AssessmentException("Failed to get assessment media: " + ex.getMessage(), ex);
        }
    }

    @Override
    public boolean updateAssessmentById(Long courseId, Long assessmentId, Assessment assessment, MultipartFile file, String authorizationHeader) {
        try {
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);
            Course course = courseService.getCourseByCourseId(courseId);

            if (course != null && isAuthorizedAsInstructor(userSharedDto.getRole(), userSharedDto.getId(), courseId)) {
                Optional<Assessment> optionalAssessment = course.getAssessments().stream()
                        .filter(a -> a.getId().equals(assessmentId))
                        .findFirst();

                if (optionalAssessment.isPresent()) {
                    Assessment existingAssessment = optionalAssessment.get();
                    existingAssessment.setAssessmentTitle(assessment.getAssessmentTitle());
                    existingAssessment.setPercentage(assessment.getPercentage());

                    // Check if a new file is uploaded
                    if (file != null && !file.isEmpty()) {
                        // Delete the previous file if it exists
                        AssessmentMedia existingMedia = existingAssessment.getAssessmentMedia();
                        if (existingMedia != null) {
                            String previousFilePath = existingMedia.getMediaFilePath();
                            File previousFile = new File(previousFilePath);
                            if (previousFile.exists() && !previousFile.delete()) {
                                logger.warn("Failed to delete previous file: {}", previousFilePath);
                            }
                        }

                        // Save the new file
                        String mediaFilePath = FOLDER_PATH + file.getOriginalFilename();
                        file.transferTo(new File(mediaFilePath));

                        // Update the AssessmentMedia entity or create a new one if it doesn't exist
                        AssessmentMedia assessmentMedia = existingMedia != null ? existingMedia : new AssessmentMedia();
                        assessmentMedia.setMediaName(file.getOriginalFilename());
                        assessmentMedia.setType(file.getContentType());
                        assessmentMedia.setMediaFilePath(mediaFilePath);
                        assessmentMedia.setAssessment(existingAssessment);

                        // Save or update the AssessmentMedia entity
                        assessmentMediaRepository.save(assessmentMedia);

                        // Set the AssessmentMedia entity in the Assessment entity
                        existingAssessment.setAssessmentMedia(assessmentMedia);
                    }

                    // Save the updated assessment
                    assessmentRepository.save(existingAssessment);
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
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
