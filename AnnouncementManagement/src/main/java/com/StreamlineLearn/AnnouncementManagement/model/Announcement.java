package com.StreamlineLearn.AnnouncementManagement.model;

import jakarta.persistence.*;


@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String announcementTile;
    private String announcement;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Announcement() {
    }

    public Announcement(Long id, String announcementTile, String announcement, Course course) {
        this.id = id;
        this.announcementTile = announcementTile;
        this.announcement = announcement;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
