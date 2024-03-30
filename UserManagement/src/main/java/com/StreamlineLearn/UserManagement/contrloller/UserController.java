package com.StreamlineLearn.UserManagement.contrloller;

import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/creates")
    public ResponseEntity<String> createUser(@RequestBody User newUser){
        userService.createUser(newUser);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable Long id,@RequestBody User updateUser){
       User userUpdated = userService.updateUser(id, updateUser);
       if(userUpdated!=null) {
            return new ResponseEntity<>("user details updated!", HttpStatus.OK);
        }
       else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        boolean userDeleted = userService.deleteUser(id);
        if(userDeleted){
            return new ResponseEntity<>("user deleted successfully!", HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
