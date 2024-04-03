package com.StreamlineLearn.AssessmentManagement.controller;

import com.StreamlineLearn.AssessmentManagement.model.Assessment;
import com.StreamlineLearn.AssessmentManagement.service.AssessmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/assessments")
public class AssessmentController {
    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping
    public ResponseEntity<String> createAssessment(@PathVariable Long courseId,
                                                   @RequestBody Assessment assessment,
                                                   @RequestHeader("Authorization") String authorizationHeader){

       String response =  assessmentService.createAssessment(courseId,assessment, authorizationHeader );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    @PostMapping("/{courseId}/enroll")
//    public ResponseEntity<String> enrollStudent(@PathVariable Long courseId,
//                                                @RequestHeader("Authorization") String authorizationHeader) {
//
//        courseService.enrollStudent(courseId, authorizationHeader);
//        return new ResponseEntity<>("Enrolled student in the course successfully", HttpStatus.OK);
//    }
//
//    @GetMapping("/{courseId}/content")
//    public ResponseEntity<CourseExternal> getCourseContent(@PathVariable Long courseId,
//                                                   @RequestHeader("Authorization") String authorizationHeader) {
//
//        CourseExternal course = courseService.getCourseContent(courseId, authorizationHeader);
//        return new ResponseEntity<>(course, HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity <List<CourseExternal>> getAllTheCourse(){
//        return new ResponseEntity<>(courseService.getAllTheCourse(),HttpStatus.OK);
//    }

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