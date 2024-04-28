package com.StreamlineLearn.UserManagement.contrloller;

import com.StreamlineLearn.SharedModule.annotation.IsAdministrative;
import com.StreamlineLearn.SharedModule.annotation.IsStudent;
import com.StreamlineLearn.UserManagement.jwtUtil.JwtService;
import com.StreamlineLearn.UserManagement.model.Student;
import com.StreamlineLearn.UserManagement.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;
    private final JwtService jwtService;

    public StudentController(StudentService studentService, JwtService jwtService) {
        this.studentService = studentService;
        this.jwtService = jwtService;
    }

    @GetMapping()
    @IsAdministrative
    public ResponseEntity<List<Student>> getAllStudent(){
        return new ResponseEntity<>(studentService.getAllStudent(), HttpStatus.OK);
    }

    @GetMapping("/student-profile")
    @IsStudent
    public ResponseEntity<Student> getStudentById(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(studentService
                .getStudentById(jwtService
                        .extractRoleId(token.substring(7)))
                , HttpStatus.OK);
    }

    @PutMapping("/student-profile/update")
    @IsStudent
    public ResponseEntity<String> updateStudent(@RequestHeader("Authorization") String token,
                                                @RequestBody Student updateStudent ) {
        boolean studentUpdated = studentService.updateStudent(jwtService
                .extractRoleId(token.substring(7)), updateStudent);
        // If the Student is found and updated, return a response indicating that the student profile has been updated
        if(studentUpdated) {
            return new ResponseEntity<>("student updated successfully", HttpStatus.OK);
        }
        // If the student is not found, return NOT_FOUND status
        return new ResponseEntity<>("student did not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/student-profile/delete")
    @IsStudent
    public ResponseEntity<String> deleteStudentById(@RequestHeader("Authorization") String token) {
        boolean studentDeleted = studentService.deleteStudentById(jwtService
                .extractRoleId(token.substring(7)));
        if(studentDeleted) {
            return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
    }

}


