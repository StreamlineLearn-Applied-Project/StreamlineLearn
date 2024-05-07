package com.StreamlineLearn.AssessmentManagement.service;

import com.StreamlineLearn.AssessmentManagement.dto.AssessmentDto;
import com.StreamlineLearn.AssessmentManagement.model.Assessment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public interface AssessmentService {
    Assessment createAssessment(Long courseId, Assessment assessment, MultipartFile file, String authorizationHeader);

    Set<Assessment> getAssessmentsByCourseId(Long courseId,String authorizationHeader);

    Optional<Assessment> getAssessmentById(Long courseId, Long assessmentId, String authorizationHeader);

    byte[] getAssessmentMedia(Long courseId, String fileName, String authorizationHeader) throws IOException;

    boolean updateAssessmentById(Long courseId, Long assessmentId, Assessment assessment, MultipartFile file, String authorizationHeader);

    boolean deleteAssessmentById(Long courseId, Long assessmentId, String authorizationHeader);
}
