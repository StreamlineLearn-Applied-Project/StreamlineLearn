package com.StreamlineLearn.UserManagement.service;

import com.StreamlineLearn.UserManagement.model.UserWithRoleRequest;

public interface UserWithRoleRequestService {
    public void registerUserWithRole(UserWithRoleRequest userRequest);


    String loginUserWithRole(UserWithRoleRequest userLoginWithRole);
}
