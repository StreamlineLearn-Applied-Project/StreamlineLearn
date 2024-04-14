package com.StreamlineLearn.UserManagement.contrloller;

import com.StreamlineLearn.UserManagement.annotation.IsAdministrative;
import com.StreamlineLearn.UserManagement.annotation.IsAuthorizedToDeleteStudent;
import com.StreamlineLearn.UserManagement.annotation.IsAuthorizedToUpdateStudent;
import com.StreamlineLearn.UserManagement.annotation.IsStudent;
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

    @IsAdministrative
    @PostMapping("/creates")
    public ResponseEntity<String> createStudent(@RequestBody Student student){
        studentService.createStudent(student);
        return new ResponseEntity<>(" student created successfully" , HttpStatus.CREATED);
    }

    @IsAdministrative
    @GetMapping()
    public ResponseEntity<List<Student>> getAllStudent(){
        return new ResponseEntity<>(studentService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/account-profile")
    public ResponseEntity<Student> getUserById(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(studentService
                .getUserById(jwtService
                        .extractRoleId(token.substring(7)))
                , HttpStatus.OK);
    }

    @PutMapping("/account-profile/edit")
    @IsAuthorizedToUpdateStudent
    public ResponseEntity<String> updateStudent(@RequestHeader("Authorization") String token,
                                                @RequestBody Student updateStudent ) {
        boolean studentUpdated = studentService.updateStudent(jwtService
                .extractRoleId(token.substring(7)), updateStudent);

        if(studentUpdated) {
            return new ResponseEntity<>("student updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("student did not found", HttpStatus.NOT_FOUND);
    }

    @IsAuthorizedToDeleteStudent
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long id) {
        boolean studentDeleted = studentService.deleteStudentById(id);
        if(studentDeleted) {
            return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
    }

}
