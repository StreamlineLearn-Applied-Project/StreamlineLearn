package com.StreamlineLearn.AssessmentManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
public class AssessmentMedia {
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
    @JoinColumn(name = "assessment_id")
    @JsonIgnore
    private Assessment assessment;

    public AssessmentMedia() {
    }

    public AssessmentMedia(Long id, String mediaName, String type, String mediaFilePath, Assessment assessment) {
        this.id = id;
        this.mediaName = mediaName;
        this.type = type;
        this.mediaFilePath = mediaFilePath;
        this.assessment = assessment;
    }

    public static class Builder {
        private Long id;
        private String mediaName;
        private String type;
        private String mediaFilePath;
        private Assessment assessment;

        public Builder id(Long id) {
            this.id = id;
            return this;
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

        public Builder assessment(Assessment assessment) {
            this.assessment = assessment;
            return this;
        }

        public AssessmentMedia build() {
            AssessmentMedia assessmentMedia = new AssessmentMedia();
            assessmentMedia.id = this.id;
            assessmentMedia.mediaName = this.mediaName;
            assessmentMedia.type = this.type;
            assessmentMedia.mediaFilePath = this.mediaFilePath;
            assessmentMedia.assessment = this.assessment;
            return assessmentMedia;
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

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }
}

