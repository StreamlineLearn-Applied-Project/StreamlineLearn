package com.StreamlineLearn.UserManagement.contrloller;

import com.StreamlineLearn.UserManagement.annotation.IsAdministrative;
import com.StreamlineLearn.UserManagement.jwtUtil.JwtService;
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
    private final JwtService jwtService;

    public AdministrativeController(AdministrativeService administrativeService,
                                    JwtService jwtService) {
        this.administrativeService = administrativeService;
        this.jwtService = jwtService;
    }

    @PostMapping("/creates")
    @IsAdministrative
    public ResponseEntity<String> createAdministrative(@RequestBody Administrative newAdministrative){
        administrativeService.createAdministrative(newAdministrative);
        return new ResponseEntity<>("new Administrative created successfully", HttpStatus.CREATED);
    }

    @GetMapping()
    @IsAdministrative
    public ResponseEntity<List<Administrative>> getAllAdministrative(){
        return new ResponseEntity<>(administrativeService.getAllAdministrative(), HttpStatus.OK);
    }

    @GetMapping("/administrative-profile")
    public ResponseEntity<Administrative> getAdministrativeById(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(administrativeService
                .getAdministrativeById(jwtService
                        .extractRoleId(token.substring(7)))
                , HttpStatus.OK);
    }

    @PutMapping("/administrative-profile/update")
    public ResponseEntity<String> updateAdministrative(@RequestHeader("Authorization") String token,
                                                       @RequestBody Administrative updateAdministrative ) {
        boolean administrativeUpdated = administrativeService.updateAdministrative(jwtService
                .extractRoleId(token.substring(7)), updateAdministrative);

        // If the Administrative is found and updated, return a response indicating that the Administrative profile has been updated
        if(administrativeUpdated) {
            return new ResponseEntity<>("Administrative updated successfully", HttpStatus.OK);
        }
        // If the Administrative is not found, return NOT_FOUND status
        return new ResponseEntity<>("Administrative did not found", HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/administrative-profile/delete")
    public ResponseEntity<String> deleteAdministrativeById(@RequestHeader("Authorization") String token) {
        boolean administrativeDeleted = administrativeService.deleteAdministrativeById(jwtService
                .extractRoleId(token.substring(7)));
        if(administrativeDeleted) {
            return new ResponseEntity<>("Administrative Deleted deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Administrative not found", HttpStatus.NOT_FOUND);
    }
}
