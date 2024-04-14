package com.StreamlineLearn.UserManagement.contrloller;

import com.StreamlineLearn.UserManagement.dto.UserDto;
import com.StreamlineLearn.UserManagement.service.UserSignUpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "*")
public class UserSignUpController {
    private final UserSignUpService userSignUpService;

    public UserSignUpController(UserSignUpService userSignUpService) {
        this.userSignUpService = userSignUpService;
    }

    @PostMapping
    public ResponseEntity<String> userSignUp(@RequestBody UserDto userRequest) {
        userSignUpService.userRegister(userRequest);
        return new ResponseEntity<>("Account created successfully " , HttpStatus.CREATED);
    }
}
