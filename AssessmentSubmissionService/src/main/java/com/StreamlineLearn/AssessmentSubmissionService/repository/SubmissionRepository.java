package com.StreamlineLearn.AssessmentSubmissionService.repository;

import com.StreamlineLearn.AssessmentSubmissionService.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findAllByAssessmentId(Long assessmentId);

    Submission findSubmissionByAssessmentId(Long assessmentId);
}
