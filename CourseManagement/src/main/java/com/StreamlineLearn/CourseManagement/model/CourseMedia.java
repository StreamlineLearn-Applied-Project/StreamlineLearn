package com.StreamlineLearn.CourseManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
public class CourseMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mediaName;
    private String type;
    private String mediaFilePath;
    @CreationTimestamp
    private Date creationDate;
    @UpdateTimestamp
    private Date lastUpdated;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;

    public CourseMedia() {
    }

    public CourseMedia(Long id, String mediaName, String type, String mediaFilePath, Course course) {
        this.id = id;
        this.mediaName = mediaName;
        this.type = type;
        this.mediaFilePath = mediaFilePath;
        this.course = course;
    }

    public static class Builder {
        private Long id;
        private String mediaName;
        private String type;
        private String mediaFilePath;
        private Course course;

        public Builder() {
        }

        public Builder(Long id, String mediaName, String type, String mediaFilePath, Course course) {
            this.id = id;
            this.mediaName = mediaName;
            this.type = type;
            this.mediaFilePath = mediaFilePath;
            this.course = course;
        }

        public Builder mediaName(String mediaName) {
            this.mediaName = mediaName;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder mediaFilePath(String mediaFilePath) {
            this.mediaFilePath = mediaFilePath;
            return this;
        }

        public Builder course(Course course) {
            this.course = course;
            return this;
        }

        public CourseMedia build() {
            CourseMedia courseMedia = new CourseMedia();
            courseMedia.id = this.id;
            courseMedia.mediaName = this.mediaName;
            courseMedia.type = this.type;
            courseMedia.mediaFilePath = this.mediaFilePath;
            courseMedia.course = this.course;
            return courseMedia;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMediaFilePath() {
        return mediaFilePath;
    }

    public void setMediaFilePath(String mediaFilePath) {
        this.mediaFilePath = mediaFilePath;
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

