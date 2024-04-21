package com.StreamlineLearn.AssessmentManagement.service;

import com.StreamlineLearn.AssessmentManagement.model.Assessment;

import java.util.Set;

public interface AssessmentService {
    void createAssessment(Long courseId, Assessment assessment, String authorizationHeader);

    Assessment getAssessmentById(Long id);

    boolean updateAssessmentById(Long id, Assessment assessment);

    boolean deleteAssessmentById(Long id);

    Set<Assessment> getAssessmentByCourseId(Long id);
}
