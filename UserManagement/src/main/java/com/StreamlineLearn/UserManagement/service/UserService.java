package com.StreamlineLearn.UserManagement.service;

import com.StreamlineLearn.UserManagement.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    User createUser(User user);


    List<User> getAllUser();

    public User getUserById(Long id);

    public User updateUser(Long id, User updateUser);

    public boolean deleteUser(Long id);

}
