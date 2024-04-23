package com.StreamlineLearn.AssessmentManagement.controller;

import com.StreamlineLearn.AssessmentManagement.dto.AssessmentDto;
import com.StreamlineLearn.AssessmentManagement.model.Assessment;
import com.StreamlineLearn.AssessmentManagement.service.AssessmentService;
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
    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping
    public ResponseEntity<String> createAssessment(@PathVariable Long courseId,
                                                   @RequestBody Assessment assessment,
                                                   @RequestHeader("Authorization") String authorizationHeader){

        assessmentService.createAssessment(courseId, assessment, authorizationHeader );
        return new ResponseEntity<>("Assessment Added successfully.", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<Assessment>> getAssessmentsByCourseId(@PathVariable Long courseId){
        Set<Assessment> assessments = assessmentService.getAssessmentsByCourseId(courseId);
        if(assessments != null && !assessments.isEmpty()){
            return new ResponseEntity<>(assessments, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{assessmentId}")
    public ResponseEntity<AssessmentDto> getAssessmentById(@PathVariable Long courseId,
                                                        @PathVariable Long assessmentId,
                                                        @RequestHeader("Authorization") String authorizationHeader){

        Optional<AssessmentDto> assessment = assessmentService.getAssessmentById(courseId, assessmentId, authorizationHeader);

        return assessment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{assessmentId}")
    public ResponseEntity<String> updateAssessmentById(@PathVariable Long courseId,
                                                       @PathVariable Long assessmentId,
                                                       @RequestBody Assessment assessment,
                                                       @RequestHeader("Authorization") String authorizationHeader){
        boolean assessmentUpdated = assessmentService.updateAssessmentById(courseId, assessmentId, assessment, authorizationHeader);
        if(assessmentUpdated){
            return new ResponseEntity<>("Assessment updated Successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Assessment not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{assessmentId}")
    public ResponseEntity<String> deleteAssessmentById(@PathVariable Long courseId,
                                                       @PathVariable Long assessmentId,
                                                       @RequestHeader("Authorization") String authorizationHeader){
        boolean assessmentDeleted = assessmentService.deleteAssessmentById(courseId, assessmentId, authorizationHeader);
        if(assessmentDeleted){
            return new ResponseEntity<>("Assessment deleted Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("CourseExternal not found", HttpStatus.NOT_FOUND);
        }
    }

}