package com.StreamlineLearn.AssessmentManagement.model;

import java.util.List;

public class InsertionData {
    private String courseTitle;
    private String assessmentTitle;
    private Integer assessmentPercentage;
    private List<String> studentNames;

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public Integer getAssessmentPercentage() {
        return assessmentPercentage;
    }

    public void setAssessmentPercentage(Integer assessmentPercentage) {
        this.assessmentPercentage = assessmentPercentage;
    }

    public List<String> getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(List<String> studentNames) {
        this.studentNames = studentNames;
    }
}

