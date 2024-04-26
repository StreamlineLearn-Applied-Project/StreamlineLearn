package com.StreamlineLearn.UserManagement.contrloller;

import com.StreamlineLearn.UserManagement.annotation.IsAdministrative;
import com.StreamlineLearn.UserManagement.jwtUtil.JwtService;
import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping()
    @IsAdministrative
    public ResponseEntity<List<User>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserById(@RequestHeader("Authorization") String token){
        return new ResponseEntity<>(userService
                .getUserById(jwtService
                        .extractUserId(token.substring(7)))
                , HttpStatus.OK);
    }

    @PutMapping("/profile/update")
    public ResponseEntity<String> updateUserById(@RequestHeader("Authorization") String token,
                                                 @RequestBody User updateUser){

        Optional<User> userUpdated = userService.updateUser(jwtService
                .extractUserId(token.substring(7)), updateUser);

        // If the user is found and updated, return a response indicating that the user profile has been updated
        return userUpdated.map(user -> new ResponseEntity<>("User profile updated successfully", HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND)); // If the user is not found, return NOT_FOUND status
    }


}
