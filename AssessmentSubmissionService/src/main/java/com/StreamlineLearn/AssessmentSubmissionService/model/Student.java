package com.StreamlineLearn.AssessmentSubmissionService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// --------------Student.java----------------------
@Entity
public class Student {
    @Id
    private Long id;
    private String username;
    private String role;

    // Many-to-many relationship with Course
    @ManyToMany(mappedBy = "students")
    @JsonIgnore // Ignore this field during serialization to break the infinite loop
    private Set<Course> courses = new HashSet<>();

    // One-to-many relationship with Submission
    @OneToMany(mappedBy = "student")
    @JsonIgnore // Ignore this field during serialization to break the infinite loop
    private List<Submission> submissions;

    public Student() {
    }

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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }
}
