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
import com.StreamlineLearn.SharedModule.jwtUtil.SharedJwtService;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionServiceImplementation implements SubmissionService {
    private final SharedJwtService jwtService;
    private final CourseService courseService;
    private final StudentService studentService;
    private final AssessmentService assessmentService;
    private final SubmissionRepository submissionRepository;
    private static final int TOKEN_PREFIX_LENGTH = 7;
    private static final Logger logger = LoggerFactory.getLogger(SubmissionServiceImplementation.class);

    public SubmissionServiceImplementation(SharedJwtService jwtService, CourseService courseService,
                                            StudentService studentService,AssessmentService assessmentService,
                                            SubmissionRepository submissionRepository) {
        this.jwtService = jwtService;
        this.courseService = courseService;
        this.studentService = studentService;
        this.assessmentService = assessmentService;
        this.submissionRepository = submissionRepository;
    }


    @Override
    public String submitAssessment(Long courseId, Long assessmentId,
                                 Submission submission, String authorizationHeader) {

        try {
            // Extract student ID from JWT token
            Student student = studentService.findStudentByStudentId(jwtService
                    .extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH)));

            String role = jwtService.extractRole(authorizationHeader
                    .substring(TOKEN_PREFIX_LENGTH));

            // Check if the student is enrolled in the course
            if ("STUDENT".equals(role) && courseService.isStudentEnrolled(student.getId(), courseId)) {
                // Get the assessment by ID
                Assessment assessment = assessmentService.findAssessmentById(assessmentId);

                // Set the student and assessment for the submission
                submission.setStudent(student); // Assuming Student constructor takes ID
                submission.setAssessment(assessment);

                // Save the submission
                submissionRepository.save(submission);

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

        Long instructorId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        String role = jwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        if ("INSTRUCTOR".equals(role) && courseService.isInstructorOfCourse(instructorId, courseId)) {
            return submissionRepository.findAllByAssessmentId(assessmentId);
        } else {
            throw new RuntimeException("You are not authorized to access these submissions.");
        }

    }

    public Submission getSubmissionById(Long courseId,
                                        Long assessmentId,
                                        Long submissionId,
                                        String authorizationHeader){

        Long roleId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        String role = jwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));

        if ("INSTRUCTOR".equals(role) && courseService.isInstructorOfCourse(roleId, courseId)) {
            return submission;
        }
        if ("STUDENT".equals(role) && submission.getStudent().getId().equals(roleId)){
            return submission;
        } else {
            throw new RuntimeException("You are not authorized to access these submissions.");
        }
    }

    @Override
    public void updateSubmission(Long courseId, Long assessmentId, Long submissionId, Submission submission, String authorizationHeader) {
        Long studentId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        String role = jwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        Submission existingSubmission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));

        if (!"STUDENT".equals(role) || !existingSubmission.getStudent().getId().equals(studentId)) {
            throw new RuntimeException("You are not authorized to update this submission.");
        }
        existingSubmission.setFileUrl(submission.getFileUrl());
        existingSubmission.setComments(submission.getComments());
        submissionRepository.save(existingSubmission);
    }

    @Override
    public void deleteSubmission(Long courseId, Long assessmentId, Long submissionId, String authorizationHeader) {
        Long studentId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        String role = jwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Submission existingSubmission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));
        if (!"STUDENT".equals(role) || !existingSubmission.getStudent().getId().equals(studentId)) {
            throw new RuntimeException("You are not authorized to delete this submission.");
        }
        submissionRepository.delete(existingSubmission);
    }

}
