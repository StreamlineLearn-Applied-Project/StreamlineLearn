package com.StreamlineLearn.SharedModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseAssessmentDto {
    private Long instructorId;
    private Long courseId;
    private Long assessmentId;

}
