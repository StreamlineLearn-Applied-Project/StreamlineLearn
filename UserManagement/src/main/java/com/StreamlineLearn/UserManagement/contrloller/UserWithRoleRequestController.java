package com.StreamlineLearn.UserManagement.contrloller;

import com.StreamlineLearn.UserManagement.model.*;
import com.StreamlineLearn.UserManagement.service.UserWithRoleRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("authentication")
@CrossOrigin(origins = "*")
public class UserWithRoleRequestController {

    private final UserWithRoleRequestService userWithRoleRequestService;

    public UserWithRoleRequestController(UserWithRoleRequestService userWithRoleRequestService) {
        this.userWithRoleRequestService = userWithRoleRequestService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUserWithRole(@RequestBody UserWithRoleRequest userRequest) {
        userWithRoleRequestService.registerUserWithRole(userRequest);

        String field = String.valueOf(userRequest.getStudent().getField());

        return new ResponseEntity<>("Account created successfully " + field, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUserWithRole(@RequestBody UserWithRoleRequest userLoginWithRole){
        String token = userWithRoleRequestService.loginUserWithRole(userLoginWithRole);
        if(token!=null){
        return new ResponseEntity<>( token, HttpStatus.OK);
        }else {
            return new ResponseEntity<>( "login failed", HttpStatus.NOT_FOUND);
        }
    }

}

