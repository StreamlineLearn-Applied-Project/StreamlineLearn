package com.StreamlineLearn.AssessmentManagement.controller;


import com.StreamlineLearn.AssessmentManagement.model.Assessment;
import com.StreamlineLearn.AssessmentManagement.service.AssessmentService;
import com.StreamlineLearn.SharedModule.annotation.IsInstructor;
import com.StreamlineLearn.SharedModule.annotation.IsStudentOrInstructor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/courses/{courseId}/assessments")
@CrossOrigin(origins = "*")
public class AssessmentController {
    // This declares a dependency on an AssessmentService.
    private final AssessmentService assessmentService;

    // This is a constructor-based dependency injection of the AssessmentService.
    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    private String determineContentType(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);

        return switch (extension.toLowerCase()) {
            case "txt" -> "text/plain";
            case "pdf" -> "application/pdf";
            case "doc" -> "application/msword";
            case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls" -> "application/vnd.ms-excel";
            case "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "ppt" -> "application/vnd.ms-powerpoint";
            case "pptx" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            default -> "application/octet-stream"; // Fallback to "application/octet-stream"
        };
    }


    // Endpoint to create a new assessment
    @PostMapping //HTTP POST requests onto the createAssessment method.
    @IsInstructor // This is a custom annotation, presumably checking if the authenticated user is an instructor.
    public ResponseEntity<Assessment> createAssessment(@PathVariable Long courseId,
                                                       @RequestPart("assessment") String assessmentJson,
                                                       @RequestPart("media") MultipartFile file,
                                                       @RequestHeader("Authorization") String authorizationHeader) throws IOException {

        // Convert JSON string to an Assessment object
        Assessment assessment = new ObjectMapper().readValue(assessmentJson, Assessment.class);

        // calls the createAssessment method in the AssessmentService with the provided Assessment and authorization header.
        Assessment createdAssessment = assessmentService.createAssessment(courseId, assessment, file, authorizationHeader);

        // This returns a ResponseEntity with the created Assessment and an HTTP status code
        // indicating that the assessment was successfully created.
        return new ResponseEntity<>(createdAssessment, HttpStatus.CREATED);
    }

    // Endpoint to get all assessments for a course
    @GetMapping //HTTP GET requests onto the getAssessmentsByCourseId method.
    @IsStudentOrInstructor
    public ResponseEntity<Set<Assessment>> getAssessmentsByCourseId(@PathVariable Long courseId,
                                                                    @RequestHeader("Authorization") String authorizationHeader){
        Set<Assessment> assessments = assessmentService.getAssessmentsByCourseId(courseId, authorizationHeader);
        return new ResponseEntity<>(assessments, HttpStatus.OK);
    }

    @GetMapping("/{assessmentId}")
    @IsStudentOrInstructor
    public ResponseEntity<Assessment> getAssessmentById(@PathVariable Long courseId,
                                                        @PathVariable Long assessmentId,
                                                        @RequestHeader("Authorization") String authorizationHeader){

        // Call the service method to get the assessment by ID
        Optional<Assessment> assessment = assessmentService.getAssessmentById(courseId, assessmentId, authorizationHeader);

        // If the assessment is present, return it with HTTP status code OK; otherwise, return 404
        return assessment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint to get assessment media by file name
    @GetMapping("/media/{fileName}")
    public ResponseEntity<byte[]> getAssessmentMedia(@PathVariable Long courseId,
                                                     @PathVariable String fileName,
                                                     @RequestHeader("Authorization") String authorizationHeader) throws IOException {
        byte[] fileContent = assessmentService.getAssessmentMedia(courseId, fileName, authorizationHeader);

        // Determine a content type based on the file's extension
        String contentType = determineContentType(fileName);

        // Return the media data with the determined content type
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(contentType))
                .body(fileContent);
    }

    // Endpoint to update an assessment by ID
    @PutMapping("/{assessmentId}")
    @IsInstructor // Custom annotation to check if the user is an instructor
    public ResponseEntity<String> updateAssessmentById(@PathVariable Long courseId,
                                                       @PathVariable Long assessmentId,
                                                       @RequestPart("assessment") String assessmentJson,
                                                       @RequestPart(value = "media", required = false) MultipartFile file,
                                                       @RequestHeader("Authorization") String authorizationHeader) throws JsonProcessingException {

        // Convert JSON string to an Assessment object
        Assessment assessment = new ObjectMapper().readValue(assessmentJson, Assessment.class);

        // Call the service method to update the Assessment
        boolean assessmentUpdated = assessmentService
                .updateAssessmentById(courseId, assessmentId, assessment,file, authorizationHeader);

        // If the Assessment was updated successfully, return a success message; otherwise, return 404
        return assessmentUpdated ? ResponseEntity.ok("Assessment updated Successfully") :
                ResponseEntity.notFound().build();
    }

    // Endpoint to delete an Assessment by ID
    @DeleteMapping("/{assessmentId}")
    @IsInstructor
    public ResponseEntity<String> deleteAssessmentById(@PathVariable Long courseId,
                                                       @PathVariable Long assessmentId,
                                                       @RequestHeader("Authorization") String authorizationHeader){

        boolean assessmentDeleted = assessmentService.deleteAssessmentById(courseId, assessmentId, authorizationHeader);
        // If the announcement was updated successfully, return a success message; otherwise, return 404
        return assessmentDeleted ? ResponseEntity.ok("Assessment Deleted Successfully") :
                ResponseEntity.notFound().build();
    }

}