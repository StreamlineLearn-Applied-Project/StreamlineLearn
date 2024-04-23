package com.StreamlineLearn.CourseManagement.dto;

import java.math.BigDecimal;

public class CourseDTO {
    private String courseName;
    private String description;
    private BigDecimal price;

    public CourseDTO(String courseName, String description) {
        this.courseName = courseName;
        this.description = description;
    }

    public CourseDTO(String courseName, String description, BigDecimal price) {
        this.courseName = courseName;
        this.description = description;
        this.price = price;
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
}
