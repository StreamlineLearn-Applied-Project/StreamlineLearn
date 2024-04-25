package com.StreamlineLearn.AssessmentSubmissionService.repository;

import com.StreamlineLearn.AssessmentSubmissionService.model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}
