package com.StreamlineLearn.DiscussionService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String discussion;

    private String discussionTitle;

    // Many-to-one relationship with Course
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // Many-to-many relationship with Student
    @ManyToMany
    @JoinTable(
            name = "discussion_student",
            joinColumns = @JoinColumn(name = "discussion_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    // Many-to-many relationship with Student
    @ManyToMany
    @JoinTable(
            name = "discussion_instructor",
            joinColumns = @JoinColumn(name = "discussion_id"),
            inverseJoinColumns = @JoinColumn(name = "instructor_id")
    )
    @JsonIgnore
    private Set<Instructor> instructors = new HashSet<>();

    @ElementCollection
    private List<String> discussionThreads = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscussion() {
        return discussion;
    }

    public void setDiscussion(String discussion) {
        this.discussion = discussion;
    }

    public String getDiscussionTitle() {
        return discussionTitle;
    }

    public void setDiscussionTitle(String discussionTitle) {
        this.discussionTitle = discussionTitle;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void setStudent(Student student) {
        students.add(student);
    }

    public Set<Instructor> getInstructors() {
        return instructors;
    }
    public void setInstructors(Set<Instructor> instructors) {
        this.instructors = instructors;
    }
    public void setInstructor(Instructor instructor) {
        instructors.add(instructor);
    }

    public List<String> getDiscussionThreads() {
        return discussionThreads;
    }

    public void setDiscussionThreads(List<String> discussionThreads) {
        this.discussionThreads = discussionThreads;
    }
}

