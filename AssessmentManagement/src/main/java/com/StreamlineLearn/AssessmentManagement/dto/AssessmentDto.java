package com.StreamlineLearn.AssessmentManagement.dto;

public class AssessmentDto {
    private String title;
    private Integer percentage;

    public AssessmentDto(String title, Integer percentage) {
        this.title = title;
        this.percentage = percentage;
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
}
