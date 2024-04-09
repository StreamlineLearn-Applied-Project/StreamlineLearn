package com.StreamlineLearn.UserManagement.service;


import com.StreamlineLearn.UserManagement.dto.AuthenticationResponse;
import com.StreamlineLearn.UserManagement.model.User;

public interface AuthenticationService {
    public AuthenticationResponse authenticate(User request);
}
