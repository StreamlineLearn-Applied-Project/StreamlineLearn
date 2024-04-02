package com.StreamlineLearn.AssessmentManagement.repository;

import com.StreamlineLearn.AssessmentManagement.model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}
