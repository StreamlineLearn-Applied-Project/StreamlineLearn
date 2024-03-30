package com.StreamlineLearn.UserManagement.contrloller;

import com.StreamlineLearn.UserManagement.Dto.CourseDto;
import com.StreamlineLearn.UserManagement.external.Course;
import com.StreamlineLearn.UserManagement.model.Instructor;
import com.StreamlineLearn.UserManagement.service.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/instructor")
public class InstructorController {

    private final InstructorService instructorService;
    private final RestTemplate restTemplate; //Testing Purpose

    public InstructorController(InstructorService instructorService,
                                RestTemplate restTemplate ) {
        this.instructorService = instructorService;
        this.restTemplate = restTemplate; //Testing Purpose
    }

    @PostMapping("/creates")
    public ResponseEntity<String> createInstructor(@RequestBody Instructor newInstructor){
        instructorService.createInstructor(newInstructor);
        return new ResponseEntity<>("Instructor created successfully" , HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Instructor>> getAllInstructor(){
        return new ResponseEntity<>(instructorService.getAllInstructor(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instructor> getUserById(@PathVariable Long id){
        return new ResponseEntity<>(instructorService.getInstructorById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateInstructor(@PathVariable Long id,
                                                   @RequestBody Instructor updateInstructor ) {

        boolean instructorUpdated = instructorService.updateInstructor(id, updateInstructor);
        if(instructorUpdated) {
            return new ResponseEntity<>("instructor updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("instructor did not found", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInstructorById(@PathVariable Long id) {

        boolean instructorDeleted = instructorService.deleteInstructorById(id);
        if(instructorDeleted) {
            return new ResponseEntity<>("instructorDeleted deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("instructor not found", HttpStatus.NOT_FOUND);
    }

    //Testing Purpose
    @PostMapping("/postCourses")
    public ResponseEntity<String> postACourse(@RequestBody CourseDto courseDto){

        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setPrice(courseDto.getPrice());
        course.setInstructorId(courseDto.getInstructorId());

        // Make HTTP POST request to Course Management service
        return restTemplate.postForEntity("http://localhost:8282/courses", course, String.class);
    }
}
