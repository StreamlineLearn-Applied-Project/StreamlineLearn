package com.StreamlineLearn.CourseManagement.controller;


import com.StreamlineLearn.CourseManagement.model.Course;
import com.StreamlineLearn.CourseManagement.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id){
        Course course = courseService.getCourseById(id);
        if(course != null){
        return new ResponseEntity<>(course, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourseById(@PathVariable Long id, @RequestBody Course course){
        boolean courseUpdated = courseService.updateCourseById(id, course);
        if(courseUpdated){
        return new ResponseEntity<>("Course updated Successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourseById(@PathVariable Long id){
        boolean courseDeleted = courseService.deleteCourseById(id);
        if(courseDeleted){
        return new ResponseEntity<>("Course deleted Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
    }

}
