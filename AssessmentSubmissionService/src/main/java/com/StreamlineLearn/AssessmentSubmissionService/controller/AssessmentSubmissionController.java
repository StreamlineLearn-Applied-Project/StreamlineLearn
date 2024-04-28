package com.StreamlineLearn.AssessmentSubmissionService.controller;

import com.StreamlineLearn.AssessmentSubmissionService.model.Submission;
import com.StreamlineLearn.AssessmentSubmissionService.service.SubmissionService;
import com.StreamlineLearn.SharedModule.annotation.IsInstructor;
import com.StreamlineLearn.SharedModule.annotation.IsStudent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/assessments/{assessmentId}/submission")
public class AssessmentSubmissionController {
    private final SubmissionService submissionService;

    public AssessmentSubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping
    @IsStudent
    public ResponseEntity<String> submitAssessment(@PathVariable("courseId") Long courseId,
                                                   @PathVariable("assessmentId") Long assessmentId,
                                                   @RequestBody Submission submission,
                                                   @RequestHeader("Authorization") String authorizationHeader) {

        submissionService.submitAssessment(courseId, assessmentId, submission, authorizationHeader);

        return null;
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

