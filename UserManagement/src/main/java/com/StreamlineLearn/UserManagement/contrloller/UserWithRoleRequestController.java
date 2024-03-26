package com.StreamlineLearn.UserManagement.contrloller;

import com.StreamlineLearn.UserManagement.model.*;
import com.StreamlineLearn.UserManagement.service.UserWithRoleRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@CrossOrigin(origins = "*")
public class UserWithRoleRequestController {

    private final UserWithRoleRequestService userWithRoleRequestService;

    public UserWithRoleRequestController(UserWithRoleRequestService userWithRoleRequestService) {
        this.userWithRoleRequestService = userWithRoleRequestService;
    }

    @PostMapping("/createUserWithRoles")
    public ResponseEntity<String> createUserWithRole(@RequestBody UserWithRoleRequest userRequest) {
        userWithRoleRequestService.createUserWithRole(userRequest);

        return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
    }
}
