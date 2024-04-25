package com.StreamlineLearn.AssessmentSubmissionService.service;

import com.StreamlineLearn.AssessmentSubmissionService.model.Assessment;
import com.StreamlineLearn.SharedModule.dto.CourseAssessmentDto;

public interface AssessmentService {
    void saveAssessment(CourseAssessmentDto courseAssessmentDto);

    Assessment findAssessmentById(Long assessmentId);
}
