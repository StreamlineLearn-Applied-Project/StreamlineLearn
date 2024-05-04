package com.StreamlineLearn.ContentManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@Entity
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 150)
    private String contentTitle;
    @NotNull
    @Lob
    private String contentDescription;
    @CreationTimestamp
    private Date creationDate;
    @UpdateTimestamp
    private Date lastUpdated;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToOne(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    private ContentMedia contentMedia;

    public Content() {
    }

    public Content(Long id, String contentTitle,
                   String contentDescription,
                   Date creationDate,
                   Date lastUpdated,
                   Course course,
                   ContentMedia contentMedia) {
        this.id = id;
        this.contentTitle = contentTitle;
        this.contentDescription = contentDescription;
        this.creationDate = creationDate;
        this.lastUpdated = lastUpdated;
        this.course = course;
        this.contentMedia = contentMedia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
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

    public ContentMedia getContentMedia() {
        return contentMedia;
    }

    public void setContentMedia(ContentMedia contentMedia) {
        this.contentMedia = contentMedia;
    }
}
