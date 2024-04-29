package com.StreamlineLearn.SharedModule.dto;

public class EnrolledStudentDto {
    private Long id;
    private String userName;
    private String role;
    private Long courseId;

    public EnrolledStudentDto() {
    }

    public EnrolledStudentDto(Long id, String userName, String role, Long courseId) {
        this.id = id;
        this.userName = userName;
        this.role = role;
        this.courseId = courseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
