package com.StreamlineLearn.AssessmentManagement.controller;

import com.StreamlineLearn.AssessmentManagement.model.Assessment;
import com.StreamlineLearn.AssessmentManagement.service.AssessmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<Set<Assessment>> getAssessmentByCourseId(@PathVariable Long courseId){
        Set<Assessment> assessments = assessmentService.getAssessmentByCourseId(courseId);
        if(assessments != null && !assessments.isEmpty()){
            return new ResponseEntity<>(assessments, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assessment> getAssessmentById(@PathVariable Long id){
        Assessment assessment = assessmentService.getAssessmentById(id);
        if(assessment != null){
            return new ResponseEntity<>(assessment, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAssessmentById(@PathVariable Long id, @RequestBody Assessment assessment,
                                                       @RequestHeader("Authorization") String authorizationHeader){
        boolean assessmentUpdated = assessmentService.updateAssessmentById(id, assessment);
        if(assessmentUpdated){
            return new ResponseEntity<>("Assessment updated Successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Assessment not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssessmentById(@PathVariable Long id){
        boolean assessmentDeleted = assessmentService.deleteAssessmentById(id);
        if(assessmentDeleted){
            return new ResponseEntity<>("Assessment deleted Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("CourseExternal not found", HttpStatus.NOT_FOUND);
        }
    }

}