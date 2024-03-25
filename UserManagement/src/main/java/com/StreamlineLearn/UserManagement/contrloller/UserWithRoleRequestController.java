package com.StreamlineLearn.UserManagement.contrloller;

import com.StreamlineLearn.UserManagement.model.*;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class UserWithRoleRequestController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createUserWithRoles")
    public ResponseEntity<String> createUserWithRole(@RequestBody UserWithRoleRequest userRequest) {
        try {
            User user = new User();
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setUserName(userRequest.getUserName());
            user.setPassword(userRequest.getPassword());

            // Based on the role specified in the request, create the corresponding entity
            switch (userRequest.getRole()) {
                case "student":
                    Student student = new Student();
                    student.setUser(user);
                    student.setStudentId(userRequest.getRoleDetails().get("studentId"));
                    student.setMajor(userRequest.getRoleDetails().get("major"));
                    user.setStudents(Collections.singletonList(student));
                    break;
                case "instructor":
                    Instructor instructor = new Instructor();
                    instructor.setUser(user);
                    instructor.setDepartment(userRequest.getRoleDetails().get("department"));
                    instructor.setExpertise(userRequest.getRoleDetails().get("expertise"));
                    user.setInstructors(Collections.singletonList(instructor));
                    break;
                case "administrative":
                    Administrative administrative = new Administrative();
                    administrative.setUser(user);
                    administrative.setResponsibilities(userRequest.getRoleDetails().get("responsibilities"));
                    user.setAdministrative(Collections.singletonList(administrative));
                    break;
                default:
                    return ResponseEntity.badRequest().body("Invalid role specified.");
            }

            userRepository.save(user);

            return ResponseEntity.ok("User created successfully with role: " + userRequest.getRole());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }
}
