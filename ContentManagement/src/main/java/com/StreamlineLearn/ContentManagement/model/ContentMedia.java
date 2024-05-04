package com.StreamlineLearn.ContentManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
public class ContentMedia {
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
    @JoinColumn(name = "content_id")
    @JsonIgnore
    private Content content;

    public ContentMedia() {
    }

    public ContentMedia(Long id, String mediaName, String type, String mediaFilePath, Content content) {
        this.id = id;
        this.mediaName = mediaName;
        this.type = type;
        this.mediaFilePath = mediaFilePath;
        this.content = content;
    }

    public static class Builder {
        private Long id;
        private String mediaName;
        private String type;
        private String mediaFilePath;
        private Content content;

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

        public Builder content(Content content) {
            this.content = content;
            return this;
        }

        public ContentMedia build() {
            ContentMedia contentMedia = new ContentMedia();
            contentMedia.id = this.id;
            contentMedia.mediaName = this.mediaName;
            contentMedia.type = this.type;
            contentMedia.mediaFilePath = this.mediaFilePath;
            contentMedia.content = this.content;
            return contentMedia;
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

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
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
}
