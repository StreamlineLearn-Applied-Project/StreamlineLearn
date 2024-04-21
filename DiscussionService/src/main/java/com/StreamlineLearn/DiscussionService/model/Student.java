package com.StreamlineLearn.DiscussionService.model;

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

    // Many-to-many relationship with Course
    @ManyToMany(mappedBy = "students")
    @JsonIgnore // Ignore this field during serialization to break the infinite loop
    private Set<Course> courses = new HashSet<>();

    // Many-to-many relationship with Discussion
    @ManyToMany(mappedBy = "students")
    @JsonIgnore // Ignore this field during serialization to break the infinite loop
    private List<Discussion> discussions;

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

    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
    }


}
