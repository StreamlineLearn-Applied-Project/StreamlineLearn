package com.StreamlineLearn.AssessmentSubmissionService.controller;

import com.StreamlineLearn.AssessmentSubmissionService.model.Submission;
import com.StreamlineLearn.AssessmentSubmissionService.service.SubmissionService;
import com.StreamlineLearn.SharedModule.annotation.IsInstructor;
import com.StreamlineLearn.SharedModule.annotation.IsStudent;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/assessments/{assessmentId}/submission")
public class AssessmentSubmissionController {
    // This declares a dependency on a SubmissionService.
    private final SubmissionService submissionService;

    // This is a constructor-based dependency injection of the SubmissionService.
    public AssessmentSubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping //HTTP POST requests onto the submitAssessment method.
    @IsStudent // This is a custom annotation, presumably checking if the authenticated user is a student.
    public ResponseEntity<String> submitAssessment(@PathVariable("courseId") Long courseId,
                                                   @PathVariable("assessmentId") Long assessmentId,
                                                   @Valid @RequestBody Submission submission,
                                                   @RequestHeader("Authorization") String authorizationHeader) {

        // calls the submitAssessment method in the submissionService with the provided courseId,
        // assessmentId, Submission and authorization header
        String submittedAssessment = submissionService.submitAssessment(courseId, assessmentId, submission, authorizationHeader);

        // This returns a ResponseEntity with the created Submission and an HTTP status code
        // indicating that the Submission was successfully created.
        return new ResponseEntity<>(submittedAssessment, HttpStatus.CREATED);
    }

    @GetMapping
    @IsInstructor
    public ResponseEntity<List<Submission>> getAllSubmissionsForInstructor(
            @PathVariable Long courseId,
            @PathVariable Long assessmentId,
            @RequestHeader("Authorization") String authorizationHeader) {
        List<Submission> submissions = submissionService.getAllSubmissions(courseId, assessmentId, authorizationHeader);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/{submissionId}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable Long courseId,
                                                        @PathVariable Long assessmentId,
                                                        @PathVariable Long submissionId,
                                                        @RequestHeader("Authorization") String authorizationHeader) {
        Submission submission = submissionService.getSubmissionById(courseId, assessmentId, submissionId, authorizationHeader);
        return ResponseEntity.ok(submission);
    }

    @PutMapping("/{submissionId}")
    @IsStudent
    public ResponseEntity<Void> updateSubmission(@PathVariable Long courseId,
                                                 @PathVariable Long assessmentId,
                                                 @PathVariable Long submissionId,
                                                 @RequestBody Submission submission,
                                                 @RequestHeader("Authorization") String authorizationHeader) {

        submissionService.updateSubmission(courseId, assessmentId, submissionId, submission, authorizationHeader);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{submissionId}")
    @IsStudent
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long courseId,
                                                 @PathVariable Long assessmentId,
                                                 @PathVariable Long submissionId,
                                                 @RequestHeader("Authorization") String authorizationHeader) {

        submissionService.deleteSubmission(courseId, assessmentId, submissionId, authorizationHeader);
        return ResponseEntity.ok().build();
    }
}

