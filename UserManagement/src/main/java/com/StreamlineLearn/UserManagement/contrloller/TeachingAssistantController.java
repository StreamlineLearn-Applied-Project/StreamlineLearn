package com.StreamlineLearn.UserManagement.contrloller;

import com.StreamlineLearn.UserManagement.model.Administrative;
import com.StreamlineLearn.UserManagement.model.Instructor;
import com.StreamlineLearn.UserManagement.model.TeachingAssistant;
import com.StreamlineLearn.UserManagement.service.InstructorService;
import com.StreamlineLearn.UserManagement.service.TeachingAssistantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachingAssistant")
public class TeachingAssistantController {

    private final TeachingAssistantService teachingAssistantService;

    public TeachingAssistantController(TeachingAssistantService teachingAssistantService) {
        this.teachingAssistantService = teachingAssistantService;
    }

    @PostMapping("/creates")
    public ResponseEntity<String> createTeachingAssistant(@RequestBody TeachingAssistant newTeachingAssistant){
        teachingAssistantService.createTeachingAssistant(newTeachingAssistant);
        return new ResponseEntity<>("TeachingAssistant created successfully", HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<TeachingAssistant>> getAllTeachingAssistant(){
        return new ResponseEntity<>(teachingAssistantService.getAllTeachingAssistant(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeachingAssistant> getTeachingAssistantById(@PathVariable Long id){
        return new ResponseEntity<>(teachingAssistantService.getTeachingAssistantById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTeachingAssistant(@PathVariable Long id, @RequestBody TeachingAssistant updateTeachingAssistant ) {
        boolean teachingAssistantUpdated = teachingAssistantService.updateTeachingAssistant(id, updateTeachingAssistant);
        if(teachingAssistantUpdated) {
            return new ResponseEntity<>("TeachingAssistant updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("TeachingAssistant did not found", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeachingAssistantById(@PathVariable Long id) {
        boolean teachingAssistantDeleted = teachingAssistantService.deleteTeachingAssistantById(id);
        if(teachingAssistantDeleted) {
            return new ResponseEntity<>("TeachingAssistant Deleted deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("TeachingAssistant not found", HttpStatus.NOT_FOUND);
    }
}
