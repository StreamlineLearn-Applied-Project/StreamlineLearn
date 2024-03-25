package com.StreamlineLearn.UserManagement.model;

import java.util.Map;

public class UserWithRoleRequest {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String role;
    private Map<String, String> roleDetails;

    public UserWithRoleRequest(String firstName,
                               String lastName,
                               String userName,
                               String password,
                               String role,
                               Map<String,
                                       String> roleDetails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.roleDetails = roleDetails;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Map<String, String> getRoleDetails() {
        return roleDetails;
    }

    public void setRoleDetails(Map<String, String> roleDetails) {
        this.roleDetails = roleDetails;
    }
}

