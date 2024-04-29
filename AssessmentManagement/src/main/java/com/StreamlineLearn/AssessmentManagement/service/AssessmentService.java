package com.StreamlineLearn.AssessmentManagement.service;

import com.StreamlineLearn.AssessmentManagement.dto.AssessmentDto;
import com.StreamlineLearn.AssessmentManagement.model.Assessment;

import java.util.Optional;
import java.util.Set;

public interface AssessmentService {
    Assessment createAssessment(Long courseId, Assessment assessment, String authorizationHeader);

    Set<Assessment> getAssessmentsByCourseId(Long courseId,String authorizationHeader);

    Optional<Assessment> getAssessmentById(Long courseId, Long assessmentId, String authorizationHeader);

    boolean updateAssessmentById(Long courseId, Long assessmentId,Assessment assessment, String authorizationHeader);

    boolean deleteAssessmentById(Long courseId, Long assessmentId, String authorizationHeader);
}
