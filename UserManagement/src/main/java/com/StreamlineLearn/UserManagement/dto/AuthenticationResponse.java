package com.StreamlineLearn.UserManagement.dto;

import com.StreamlineLearn.UserManagement.enums.Role;

public record AuthenticationResponse(String token, Role role) {

}

