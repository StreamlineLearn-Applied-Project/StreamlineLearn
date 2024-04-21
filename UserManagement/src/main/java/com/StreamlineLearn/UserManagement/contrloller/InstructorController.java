package com.StreamlineLearn.UserManagement.contrloller;


import com.StreamlineLearn.UserManagement.annotation.IsAdministrative;
import com.StreamlineLearn.UserManagement.jwtUtil.JwtService;
import com.StreamlineLearn.UserManagement.model.Instructor;
import com.StreamlineLearn.UserManagement.service.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructor")
public class InstructorController {
    private final InstructorService instructorService;
    private final JwtService jwtService;
    public InstructorController(InstructorService instructorService,
                                JwtService jwtService) {
        this.instructorService = instructorService;
        this.jwtService = jwtService;
    }

    @GetMapping()
    @IsAdministrative
    public ResponseEntity<List<Instructor>> getAllInstructor(){
        return new ResponseEntity<>(instructorService.getAllInstructor(), HttpStatus.OK);
    }


    @GetMapping("/instructor-profile")
    public ResponseEntity<Instructor> getInstructorById(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(instructorService
                .getInstructorById(jwtService
                        .extractRoleId(token.substring(7)))
                , HttpStatus.OK);
    }

    @PutMapping("/instructor-profile/update")
    public ResponseEntity<String> updateInstructor(@RequestHeader("Authorization") String token,
                                                   @RequestBody Instructor updateInstructor ) {

        boolean instructorUpdated = instructorService.updateInstructor(jwtService
                .extractRoleId(token.substring(7)), updateInstructor);

        // If the Instructor is found and updated, return a response indicating that the instructor profile has been updated
        if(instructorUpdated) {
            return new ResponseEntity<>("instructor updated successfully", HttpStatus.OK);
        }
        // If the instructor is not found, return NOT_FOUND status
        return new ResponseEntity<>("instructor did not found", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/instructor-profile/delete")
    public ResponseEntity<String> deleteInstructorById(@RequestHeader("Authorization") String token) {

        boolean instructorDeleted = instructorService.deleteInstructorById(jwtService
                .extractRoleId(token.substring(7)));
        if(instructorDeleted) {
            return new ResponseEntity<>("instructorDeleted deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("instructor not found", HttpStatus.NOT_FOUND);
    }



}
