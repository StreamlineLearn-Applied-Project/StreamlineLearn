package com.StreamlineLearn.AssessmentSubmissionService.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String type;
    private String fileStoragePath; // File storage path
    @CreationTimestamp
    private Date creationDate;
    @UpdateTimestamp
    private Date lastUpdated;


    // Many-to-one relationship with Student
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    public Submission() {
    }

    public Submission(Long id, String fileName, String type,
                      String fileStoragePath, Date creationDate,
                      Date lastUpdated, Student student, Assessment assessment) {
        this.id = id;
        this.fileName = fileName;
        this.type = type;
        this.fileStoragePath = fileStoragePath;
        this.creationDate = creationDate;
        this.lastUpdated = lastUpdated;
        this.student = student;
        this.assessment = assessment;
    }

    // Builder class
    public static class Builder {
        private String fileName;
        private String type;
        private String fileStoragePath;
        private Student student;
        private Assessment assessment;

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder fileStoragePath(String fileStoragePath) {
            this.fileStoragePath = fileStoragePath;
            return this;
        }

        public Builder student(Student student) {
            this.student = student;
            return this;
        }

        public Builder assessment(Assessment assessment) {
            this.assessment = assessment;
            return this;
        }

        public Submission build() {
            Submission submission = new Submission();
            submission.fileName = this.fileName;
            submission.type = this.type;
            submission.fileStoragePath = this.fileStoragePath;
            submission.student = this.student;
            submission.assessment = this.assessment;
            return submission;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileStoragePath() {
        return fileStoragePath;
    }

    public void setFileStoragePath(String fileStoragePath) {
        this.fileStoragePath = fileStoragePath;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }
}

