package com.StreamlineLearn.UserManagement.contrloller;

import com.StreamlineLearn.UserManagement.model.Administrative;
import com.StreamlineLearn.UserManagement.model.Instructor;
import com.StreamlineLearn.UserManagement.service.AdministrativeService;
import com.StreamlineLearn.UserManagement.service.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrative")
public class AdministrativeController {

    private final AdministrativeService administrativeService;

    public AdministrativeController(AdministrativeService administrativeService) {
        this.administrativeService = administrativeService;
    }

    @PostMapping("/creates")
    public ResponseEntity<String> createAdministrative(@RequestBody Administrative newAdministrative){
        administrativeService.createAdministrative(newAdministrative);
        return new ResponseEntity<>("new Administrative created successfully", HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Administrative>> getAllAdministrative(){
        return new ResponseEntity<>(administrativeService.getAllAdministrative(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrative> getAdministrativeById(@PathVariable Long id){
        return new ResponseEntity<>(administrativeService.getAdministrativeById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAdministrative(@PathVariable Long id, @RequestBody Administrative updateAdministrative ) {
        boolean administrativeUpdated = administrativeService.updateAdministrative(id, updateAdministrative);
        if(administrativeUpdated) {
            return new ResponseEntity<>("Administrative updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Administrative did not found", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdministrativeById(@PathVariable Long id) {
        boolean administrativeDeleted = administrativeService.deleteAdministrativeById(id);
        if(administrativeDeleted) {
            return new ResponseEntity<>("Administrative Deleted deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Administrative not found", HttpStatus.NOT_FOUND);
    }
}
