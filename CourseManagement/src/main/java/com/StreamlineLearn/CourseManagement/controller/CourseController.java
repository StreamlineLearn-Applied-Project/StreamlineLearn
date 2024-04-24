package com.StreamlineLearn.CourseManagement.controller;


import com.StreamlineLearn.CourseManagement.dto.CourseDTO;
import com.StreamlineLearn.CourseManagement.model.Course;
import com.StreamlineLearn.CourseManagement.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*")
public class CourseController {
    private final CourseService courseService;


    public CourseController(CourseService courseService) {
        this.courseService = courseService;

    }

    @PostMapping("/create-course")
    public ResponseEntity<String> createCourse(@RequestBody Course course,
                                               @RequestHeader("Authorization") String authorizationHeader){

        courseService.createCourse(course, authorizationHeader );
        return new ResponseEntity<>("added the Course Successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <List<Course>> getAllTheCourse(){
        return new ResponseEntity<>(courseService.getAllTheCourse(),HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long courseId,
                                                   @RequestHeader(value = "Authorization", required = false) String authorizationHeader){
        Optional<CourseDTO> course = courseService.getCourseById(courseId, authorizationHeader);

        return course.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<String> updateCourseById(@PathVariable Long courseId,
                                                   @RequestBody Course course,
                                                   @RequestHeader("Authorization") String authorizationHeader){
        boolean courseUpdated = courseService.updateCourseById(courseId, course, authorizationHeader );
        if(courseUpdated){
        return new ResponseEntity<>("Course updated Successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourseById(@PathVariable Long courseId,
                                                   @RequestHeader("Authorization") String authorizationHeader){
        boolean courseDeleted = courseService.deleteCourseById(courseId, authorizationHeader);
        if(courseDeleted){
        return new ResponseEntity<>("Course deleted Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
    }

}
