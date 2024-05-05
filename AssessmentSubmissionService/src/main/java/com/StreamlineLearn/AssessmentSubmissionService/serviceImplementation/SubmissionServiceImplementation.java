package com.StreamlineLearn.AssessmentSubmissionService.serviceImplementation;


import com.StreamlineLearn.AssessmentSubmissionService.exception.AssessmentSubmissionException;
import com.StreamlineLearn.AssessmentSubmissionService.model.Assessment;
import com.StreamlineLearn.AssessmentSubmissionService.model.Student;
import com.StreamlineLearn.AssessmentSubmissionService.model.Submission;
import com.StreamlineLearn.AssessmentSubmissionService.repository.SubmissionRepository;
import com.StreamlineLearn.AssessmentSubmissionService.service.AssessmentService;
import com.StreamlineLearn.AssessmentSubmissionService.service.CourseService;
import com.StreamlineLearn.AssessmentSubmissionService.service.StudentService;
import com.StreamlineLearn.AssessmentSubmissionService.service.SubmissionService;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.StreamlineLearn.SharedModule.jwtUtil.SharedJwtService;
import com.StreamlineLearn.SharedModule.service.JwtUserService;
import com.StreamlineLearn.SharedModule.sharedException.UnauthorizedException;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubmissionServiceImplementation implements SubmissionService {
    private final JwtUserService jwtUserService;
    private final CourseService courseService;
    private final StudentService studentService;
    private final AssessmentService assessmentService;
    private final SubmissionRepository submissionRepository;
    @Value("${app.folder-path}")
    private String FOLDER_PATH;
    private static final Logger logger = LoggerFactory.getLogger(SubmissionServiceImplementation.class);

    public SubmissionServiceImplementation(JwtUserService jwtUserService, CourseService courseService,
                                           StudentService studentService, AssessmentService assessmentService,
                                           SubmissionRepository submissionRepository) {
        this.jwtUserService = jwtUserService;
        this.courseService = courseService;
        this.studentService = studentService;
        this.assessmentService = assessmentService;
        this.submissionRepository = submissionRepository;
    }

    private void deleteSubmissionFiles(String fileStoragePath) {
        try {
            Files.deleteIfExists(Paths.get(fileStoragePath));
        } catch (IOException ex) {
            // Handle file deletion exceptions
            logger.error("Error occurred while deleting submission files", ex);
            throw new AssessmentSubmissionException("Failed to delete submission files: " + ex.getMessage());
        }
    }

    // Method to check if the user is authorized to access the submission
    private void checkSubmissionAuthorization(Submission submission, String authorizationHeader) {
        // Extract role and ID from the authorization header
        UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

        // Check if the role is STUDENT and the submission belongs to the student
        if (!"STUDENT".equals(userSharedDto.getRole()) || !submission.getStudent().getId().equals(userSharedDto.getId())) {
            throw new UnauthorizedException("You are not authorized to access this submission.");
        }
    }


    @Override
    public String submitAssessment(Long courseId, Long assessmentId,
                                   MultipartFile[] files, String authorizationHeader) {
        try {
            // Extract role and ID from the authorization header
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

            Student student = studentService.findStudentByStudentId(userSharedDto.getId());

            // Check if the student is enrolled in the course
            if ("STUDENT".equals(userSharedDto.getRole()) && courseService.isStudentEnrolled(student.getId(), courseId)) {
                // Get the assessment by ID
                Assessment assessment = assessmentService.findAssessmentById(assessmentId);

                for (MultipartFile file : files) {
                    // Generate a unique file name
                    String fileName = new Date().getTime() + "_" + file.getOriginalFilename();
                    String filePath = FOLDER_PATH + File.separator + fileName;

                    // Save the file to the specified folder path
                    Files.copy(file.getInputStream(), Paths.get(filePath));

                    // Create a new Submission object
                    Submission submission = new Submission.Builder()
                            .fileName(fileName)
                            .type(file.getContentType())
                            .fileStoragePath(filePath)
                            .student(student)
                            .assessment(assessment)
                            .build();

                    // Save the submission
                    submissionRepository.save(submission);
                }
                return "Submitted Assessment successfully!";
            } else {
                throw new RuntimeException("Student is not enrolled in the course");
            }

        } catch (Exception ex){
            // Log the error and throw a custom exception of course creation fails
            logger.error("An error occurred while submitting the  Assessment", ex);
            // Rethrow the exception or throw a custom exception
            throw new AssessmentSubmissionException("Failed to submit the  Assessment: " + ex.getMessage());
        }
    }

    @Override
    public List<Submission> getAllSubmissions(Long courseId,
                                              Long assessmentId,
                                              String authorizationHeader) {
        try {
            // Extract role and ID from the authorization header
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

            if ("INSTRUCTOR".equals(userSharedDto.getRole()) && courseService.isInstructorOfCourse(userSharedDto.getId(), courseId)) {
                return submissionRepository.findAllByAssessmentId(assessmentId);
            } else {
                throw new UnauthorizedException("You are not authorized to access these submissions.");
            }
        } catch (EmptyResultDataAccessException ex) {
            // Handle a case where no submissions are found for the assessment
            throw new ResourceNotFoundException("No submissions found for the specified assessment.", ex);
        } catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic exception
            logger.error("An unexpected error occurred while fetching submissions", ex);
            throw new AssessmentSubmissionException("Failed to fetch submissions: " + ex.getMessage(), ex);
        }
    }


    @Override
    public Optional<Submission> getSubmissionById(Long courseId, Long assessmentId,
                                                  Long submissionId, String authorizationHeader) {
        try {
            // Extract role and ID from the authorization header
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

            Submission submission = submissionRepository.findById(submissionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));

            if ("INSTRUCTOR".equals(userSharedDto.getRole()) && courseService.isInstructorOfCourse(userSharedDto.getId(), courseId)) {
                return Optional.ofNullable(submission);
            }
            if ("STUDENT".equals(userSharedDto.getRole()) && submission.getStudent().getId().equals(userSharedDto.getId())) {
                return Optional.of(submission);
            } else {
                throw new UnauthorizedException("You are not authorized to access this submission.");
            }
        } catch (EmptyResultDataAccessException ex) {
            // Handle case where no submission is found for the provided ID
            throw new ResourceNotFoundException("Submission not found with ID: " + submissionId, ex);
        } catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic exception
            logger.error("An unexpected error occurred while fetching submission", ex);
            throw new AssessmentSubmissionException("Failed to fetch submission: " + ex.getMessage(), ex);
        }
    }

    @Override
    public boolean updateSubmission(Long courseId, Long assessmentId, Long submissionId,
                                 MultipartFile[] files, String authorizationHeader) {
        try {
            // Extract role and ID from the authorization header
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

            Submission existingSubmission = submissionRepository.findById(submissionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));

            if (!"STUDENT".equals(userSharedDto.getRole()) || !existingSubmission.getStudent().getId().equals(userSharedDto.getId())) {
                throw new UnauthorizedException("You are not authorized to update this submission.");
            }

            // Delete existing files associated with the submission
            deleteSubmissionFiles(existingSubmission.getFileStoragePath());

            // Save new files
            for (MultipartFile file : files) {
                String fileName = new Date().getTime() + "_" + file.getOriginalFilename();
                String filePath = FOLDER_PATH + File.separator + fileName;
                Files.copy(file.getInputStream(), Paths.get(filePath));

                // Update submission details for each file
                existingSubmission.setFileName(fileName);
                existingSubmission.setType(file.getContentType());
                existingSubmission.setFileStoragePath(filePath);
                existingSubmission.setCreationDate(new Date());
                existingSubmission.setLastUpdated(new Date());
            }

            // Save the updated submission
            submissionRepository.save(existingSubmission);

            return true;
        } catch (IOException ex) {
            // Handle IO exceptions
            logger.error("Error occurred while updating submission", ex);
            throw new AssessmentSubmissionException("Failed to update submission: " + ex.getMessage());
        }
    }

    @Override
    public byte[] getSubmissionFile(Long courseId, Long assessmentId, Long submissionId, String authorizationHeader) {
        try {
            // Retrieve the submission by ID
            Submission submission = submissionRepository.findById(submissionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));

            // Check authorization
            checkSubmissionAuthorization(submission, authorizationHeader);

            // Get the file storage path from the submission
            String fileStoragePath = submission.getFileStoragePath();

            // Read the file content
            Path filePath = Paths.get(fileStoragePath);
            return Files.readAllBytes(filePath);
        } catch (IOException ex) {
            logger.error("Error occurred while reading documentation file", ex);
            throw new AssessmentSubmissionException("Failed to get documentation file: " + ex.getMessage());
        }
    }

    @Override
    public Boolean deleteSubmission(Long courseId, Long assessmentId, Long submissionId, String authorizationHeader) {
        try {
            // Extract role and ID from the authorization header
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

            Submission existingSubmission = submissionRepository.findById(submissionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));

            if (!"STUDENT".equals(userSharedDto.getRole()) || !existingSubmission.getStudent().getId().equals(userSharedDto.getId())) {
                throw new UnauthorizedException("You are not authorized to delete this submission.");
            }

            // Delete files associated with the submission
            deleteSubmissionFiles(existingSubmission.getFileStoragePath());

            // Delete the submission
            submissionRepository.delete(existingSubmission);
            return true;
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while deleting submission", ex);
            throw new AssessmentSubmissionException("Failed to delete submission: " + ex.getMessage());
        }
    }

}
