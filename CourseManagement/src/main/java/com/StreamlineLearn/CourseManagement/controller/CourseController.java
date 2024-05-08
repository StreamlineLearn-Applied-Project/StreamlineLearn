package com.StreamlineLearn.CourseManagement.controller;


import com.StreamlineLearn.CourseManagement.dto.CourseDTO;
import com.StreamlineLearn.CourseManagement.model.Course;
import com.StreamlineLearn.CourseManagement.service.CourseService;
import com.StreamlineLearn.SharedModule.annotation.IsInstructor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*")
public class CourseController {
    // This declares a dependency on a CourseService.
    private final CourseService courseService;

    // This is a constructor-based dependency injection of the CourseService.
    public CourseController(CourseService courseService) {
        this.courseService = courseService;

    }

    private MediaType determineMediaType(String fileName) {
        // Implement logic to determine the media type based on the file extension
        // Example: Check file extension and return the corresponding MediaType
        if (fileName.endsWith(".mp4")) {
            return MediaType.valueOf("video/mp4");
        } else if (fileName.endsWith(".avi")) {
            return MediaType.valueOf("video/x-video");
        } else {
            // Default to application/octet-stream for unknown types
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    @PostMapping("/create-course") //HTTP POST requests onto the createCourse method.
    @IsInstructor // This is a custom annotation, presumably checking if the authenticated user is an instructor.
    public ResponseEntity<Course> createCourse(@RequestPart("course") String courseJson,
                                               @RequestPart("media") MultipartFile file,
                                               @RequestHeader("Authorization") String authorizationHeader) throws JsonProcessingException {
        // Convert JSON string to a Content object
        Course course = new ObjectMapper().readValue(courseJson, Course.class);

        // calls the createCourse method in the CourseService with the provided Course and authorization header.
        Course createdCourse = courseService.createCourse(course, file,  authorizationHeader );

        // This returns a ResponseEntity with the created Course and an HTTP status code
        // indicating that the course was successfully created.
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <List<Course>> getAllTheCourse(){
        return new ResponseEntity<>(courseService.getAllTheCourse(),HttpStatus.OK);
    }

    @GetMapping("/instructor-courses")
    @IsInstructor
    public ResponseEntity <Set<Course>> getAllInstructorCourse(@RequestHeader("Authorization") String authorizationHeader){
        return new ResponseEntity<>(courseService.getAllInstructorCourse(authorizationHeader),HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId,
                                                   @RequestHeader(value = "Authorization", required = false) String authorizationHeader){
        Optional<Course> course = courseService.getCourseById(courseId, authorizationHeader);

        return course.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{courseId}/media/{fileName}")
    public ResponseEntity<InputStreamResource> getCourseMedia(@PathVariable Long courseId,
                                                              @PathVariable String fileName) throws IOException {
        InputStream mediaStream = courseService.getCourseMedia(courseId, fileName);
        // Determine the appropriate media type based on the file extension
        MediaType mediaType = determineMediaType(fileName);
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(new InputStreamResource(mediaStream));
    }

    @PutMapping("/{courseId}")
    @IsInstructor
    public ResponseEntity<String> updateCourseById(@PathVariable Long courseId,
                                                   @RequestPart("course") String courseJson,
                                                   @RequestPart(value = "media", required = false) MultipartFile file,
                                                   @RequestHeader("Authorization") String authorizationHeader) throws JsonProcessingException {

        // Convert JSON string to a Content object
        Course course = new ObjectMapper().readValue(courseJson, Course.class);

        boolean courseUpdated = courseService.updateCourseById(courseId, course, file, authorizationHeader );
        if(courseUpdated){
        return new ResponseEntity<>("Course updated Successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{courseId}")
    @IsInstructor
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
