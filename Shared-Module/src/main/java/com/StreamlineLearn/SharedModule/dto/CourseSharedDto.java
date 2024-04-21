package com.StreamlineLearn.SharedModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSharedDto {
    private Long id;
    private String courseName;
    private Long instructorId;
}
