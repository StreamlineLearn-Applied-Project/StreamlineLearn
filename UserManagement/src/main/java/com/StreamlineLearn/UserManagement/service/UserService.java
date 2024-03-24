package com.StreamlineLearn.UserManagement.service;

import com.StreamlineLearn.UserManagement.model.User;

import java.util.List;

public interface UserService {

    public void createUser(User newUser);

    List<User> getAllUser();

    public User getUserById(Long id);

    public boolean updateUser(Long id, User updateUser);

    public boolean deleteUser(Long id);

}
