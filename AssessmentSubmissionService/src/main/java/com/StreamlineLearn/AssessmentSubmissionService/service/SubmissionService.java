package com.StreamlineLearn.AssessmentSubmissionService.service;

import com.StreamlineLearn.AssessmentSubmissionService.model.Submission;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface SubmissionService {
    String submitAssessment(Long courseId, Long assessmentId, MultipartFile[] files, String authorizationHeader);

    List<Submission> getAllSubmissions(Long courseId, Long assessmentId, String authorizationHeader);

    Optional<Submission> getSubmissionById(Long courseId, Long assessmentId, Long submissionId, String authorizationHeader);

    byte[] getSubmissionFile(Long courseId, Long assessmentId, Long submissionId, String authorizationHeader);

    boolean updateSubmission(Long courseId, Long assessmentId, Long submissionId, MultipartFile[] files, String authorizationHeader);

    Boolean deleteSubmission(Long courseId, Long assessmentId, Long submissionId, String authorizationHeader);

}
