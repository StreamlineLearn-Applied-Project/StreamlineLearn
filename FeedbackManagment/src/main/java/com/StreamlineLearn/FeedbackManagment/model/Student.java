package com.StreamlineLearn.FeedbackManagment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Student {
    @Id
    private Long id;
    private String username;
    private String role;

    // One-to-many relationship with Feedback
    @OneToMany(mappedBy = "student")
    @JsonIgnore // Ignore this field during serialization to break the infinite loop
    private List<Feedback> feedbackList;

    // Many-to-many relationship with Course
    @ManyToMany(mappedBy = "students")
    @JsonIgnore // Ignore this field during serialization to break the infinite loop
    private Set<Course> courses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
