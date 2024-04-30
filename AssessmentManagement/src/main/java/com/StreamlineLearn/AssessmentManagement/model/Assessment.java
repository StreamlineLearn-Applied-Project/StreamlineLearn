package com.StreamlineLearn.AssessmentManagement.model;

import jakarta.persistence.*;


@Entity
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer percentage;


    public Assessment() {
    }

    public Assessment(Long id, String title, Integer percentage, Course course) {
        this.id = id;
        this.title = title;
        this.percentage = percentage;
        this.course = course;
    }

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
