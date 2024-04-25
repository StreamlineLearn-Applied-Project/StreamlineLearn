package com.StreamlineLearn.AssessmentSubmissionService.serviceImplementation;

import com.StreamlineLearn.AssessmentSubmissionService.jwtUtil.JwtService;
import com.StreamlineLearn.AssessmentSubmissionService.model.Assessment;
import com.StreamlineLearn.AssessmentSubmissionService.model.Student;
import com.StreamlineLearn.AssessmentSubmissionService.model.Submission;
import com.StreamlineLearn.AssessmentSubmissionService.repository.SubmissionRepository;
import com.StreamlineLearn.AssessmentSubmissionService.service.AssessmentService;
import com.StreamlineLearn.AssessmentSubmissionService.service.CourseService;
import com.StreamlineLearn.AssessmentSubmissionService.service.StudentService;
import com.StreamlineLearn.AssessmentSubmissionService.service.SubmissionService;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionServiceImplementation implements SubmissionService {
    private final JwtService jwtService;
    private final CourseService courseService;
    private final StudentService studentService;
    private final AssessmentService assessmentService;
    private final SubmissionRepository submissionRepository;
    private static final int TOKEN_PREFIX_LENGTH = 7;

    public SubmissionServiceImplementation(JwtService jwtService,
                                           CourseService courseService,
                                           StudentService studentService,
                                           AssessmentService assessmentService,
                                           SubmissionRepository submissionRepository) {
        this.jwtService = jwtService;
        this.courseService = courseService;
        this.studentService = studentService;
        this.assessmentService = assessmentService;
        this.submissionRepository = submissionRepository;
    }

    @Override
    public void submitAssessment(Long courseId,
                                 Long assessmentId,
                                 Submission submission,
                                 String authorizationHeader) {

        // Extract student ID from JWT token
        Long studentId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Student student = studentService.findStudentByStudentId(studentId);
        String role = jwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        // Check if the student is enrolled in the course
        if ("STUDENT".equals(role) && courseService.isStudentEnrolled(studentId, courseId)) {
            // Get the assessment by ID
            Assessment assessment = assessmentService.findAssessmentById(assessmentId);

            // Set the student and assessment for the submission
            submission.setStudent(student); // Assuming Student constructor takes ID
            submission.setAssessment(assessment);

            // Save the submission
            submissionRepository.save(submission);
        } else {
            throw new RuntimeException("Student is not enrolled in the course");
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
