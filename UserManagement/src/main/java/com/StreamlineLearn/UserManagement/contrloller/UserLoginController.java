package com.StreamlineLearn.UserManagement.contrloller;

import com.StreamlineLearn.UserManagement.dto.AuthenticationResponse;
import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class UserLoginController {
    private final AuthenticationService authService;

    public UserLoginController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
