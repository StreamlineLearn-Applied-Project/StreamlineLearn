package com.StreamlineLearn.CourseManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @NotNull
    private String courseName;
    @NotNull
    @Lob
    private String description;
    private BigDecimal price;
    @CreationTimestamp
    private Date creationDate;
    @UpdateTimestamp
    private Date lastUpdated;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private CourseMedia courseMedia;


    public Course() {
    }

    public Course(Long id, String courseName,
                  String description, Date creationDate,
                  Date lastUpdated, Instructor
                          instructor, CourseMedia courseMedia) {
        Id = id;
        this.courseName = courseName;
        this.description = description;
        this.creationDate = creationDate;
        this.lastUpdated = lastUpdated;
        this.instructor = instructor;
        this.courseMedia = courseMedia;
    }

    public Course(Long id, String courseName,
                  String description,
                  BigDecimal price, Date creationDate,
                  Date lastUpdated, Instructor instructor,
                  CourseMedia courseMedia) {
        Id = id;
        this.courseName = courseName;
        this.description = description;
        this.price = price;
        this.creationDate = creationDate;
        this.lastUpdated = lastUpdated;
        this.instructor = instructor;
        this.courseMedia = courseMedia;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public CourseMedia getCourseMedia() {
        return courseMedia;
    }

    public void setCourseMedia(CourseMedia courseMedia) {
        this.courseMedia = courseMedia;
    }
}
