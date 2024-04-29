package com.StreamlineLearn.AssessmentSubmissionService.service;

import com.StreamlineLearn.AssessmentSubmissionService.model.Submission;

import java.util.List;

public interface SubmissionService {
    String submitAssessment(Long courseId, Long assessmentId, Submission submission, String authorizationHeader);

    List<Submission> getAllSubmissions(Long courseId, Long assessmentId, String authorizationHeader);

    void updateSubmission(Long courseId, Long assessmentId, Long submissionId, Submission submission, String authorizationHeader);

    void deleteSubmission(Long courseId, Long assessmentId, Long submissionId, String authorizationHeader);

    Submission getSubmissionById(Long courseId, Long assessmentId, Long submissionId, String authorizationHeader);
}
