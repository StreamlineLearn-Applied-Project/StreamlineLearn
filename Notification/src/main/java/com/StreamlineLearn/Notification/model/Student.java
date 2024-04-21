package com.StreamlineLearn.Notification.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {

    @Id
    private Long id;
    private String userName;
    private String role;

    // Many-to-many relationship with Course
    @ManyToMany(mappedBy = "students")
    @JsonIgnore // Ignore this field during serialization to break the infinite loop
    private Set<Course> courses = new HashSet<>();

    // Many-to-many relationship with Notification
    @ManyToMany(mappedBy = "students")
    @JsonIgnore // Ignore this field during serialization to break the infinite loop
    private Set<Notification> notifications = new HashSet<>();

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


    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }
}