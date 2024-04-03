package com.StreamlineLearn.AssessmentManagement.service;

import com.StreamlineLearn.AssessmentManagement.model.Assessment;

public interface AssessmentService {
    String createAssessment(Long courseId, Assessment assessment, String authorizationHeader);

    Assessment getAssessmentById(Long id);

    boolean updateAssessmentById(Long id, Assessment assessment);

    boolean deleteAssessmentById(Long id);
}
