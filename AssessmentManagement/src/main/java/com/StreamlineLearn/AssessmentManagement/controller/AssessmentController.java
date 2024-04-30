package com.StreamlineLearn.AssessmentManagement.controller;

import com.StreamlineLearn.AssessmentManagement.dto.AssessmentDto;
import com.StreamlineLearn.AssessmentManagement.model.Assessment;
import com.StreamlineLearn.AssessmentManagement.service.AssessmentService;
import com.StreamlineLearn.SharedModule.annotation.IsInstructor;
import com.StreamlineLearn.SharedModule.annotation.IsStudentOrInstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // Endpoint to create a new assessment
    @PostMapping //HTTP POST requests onto the createAssessment method.
    @IsInstructor // This is a custom annotation, presumably checking if the authenticated user is an instructor.
    public ResponseEntity<Assessment> createAssessment(@PathVariable Long courseId,
                                                   @Valid @RequestBody Assessment assessment,
                                                   @RequestHeader("Authorization") String authorizationHeader){

        // calls the createAssessment method in the AssessmentService with the provided Assessment and authorization header.
        Assessment createdAssessment = assessmentService.createAssessment(courseId, assessment, authorizationHeader );

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

    // Endpoint to update an assessment by ID
    @PutMapping("/{assessmentId}")
    @IsInstructor // Custom annotation to check if the user is an instructor
    public ResponseEntity<String> updateAssessmentById(@PathVariable Long courseId,
                                                       @PathVariable Long assessmentId,
                                                       @Valid @RequestBody Assessment assessment,
                                                       @RequestHeader("Authorization") String authorizationHeader){

        // Call the service method to update the Assessment
        boolean assessmentUpdated = assessmentService
                .updateAssessmentById(courseId, assessmentId, assessment, authorizationHeader);

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