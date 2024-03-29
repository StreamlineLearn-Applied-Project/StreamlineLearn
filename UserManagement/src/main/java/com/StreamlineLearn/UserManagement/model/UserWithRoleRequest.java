package com.StreamlineLearn.UserManagement.model;



public class UserWithRoleRequest {
    private User user;
    private String role;
    private Student student;
    private Instructor instructor;
    private Administrative administrative;

    public UserWithRoleRequest() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Administrative getAdministrative() {
        return administrative;
    }

    public void setAdministrative(Administrative administrative) {
        this.administrative = administrative;
    }
}

