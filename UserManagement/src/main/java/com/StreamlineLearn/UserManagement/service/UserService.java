package com.StreamlineLearn.UserManagement.service;

import com.StreamlineLearn.UserManagement.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);

    List<User> getAllUser();

    public User getUserById(Long id);

    public Optional<User> updateUser(Long id, User updateUser);


}
