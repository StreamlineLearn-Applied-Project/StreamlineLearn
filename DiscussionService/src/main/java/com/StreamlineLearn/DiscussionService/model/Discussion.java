package com.StreamlineLearn.DiscussionService.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    // Many-to-one relationship with Course
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // Many-to-many relationship with Student
    @ManyToMany(mappedBy = "discussions")
    private List<Student> students;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}

