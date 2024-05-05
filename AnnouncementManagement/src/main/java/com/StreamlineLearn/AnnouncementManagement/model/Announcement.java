package com.StreamlineLearn.AnnouncementManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String announcementTile;
    @NotNull
    @Lob
    private String announcement;
    @CreationTimestamp
    private Date creationDate;
    @UpdateTimestamp
    private Date lastUpdated;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Announcement() {
    }

    public Announcement(Long id, String announcementTile,
                        String announcement, Date creationDate,
                        Date lastUpdated, Course course) {
        this.id = id;
        this.announcementTile = announcementTile;
        this.announcement = announcement;
        this.creationDate = creationDate;
        this.lastUpdated = lastUpdated;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnnouncementTile() {
        return announcementTile;
    }

    public void setAnnouncementTile(String announcementTile) {
        this.announcementTile = announcementTile;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
