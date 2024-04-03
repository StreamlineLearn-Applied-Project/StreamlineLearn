package com.StreamlineLearn.AssessmentManagement.external;


import java.util.List;

public class CourseExternal {
    private Long Id;
    private InstructorExternal instructor;
    private List<StudentExternal> students;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }


    public InstructorExternal getInstructor() {
        return instructor;
    }

    public void setInstructor(InstructorExternal instructor) {
        this.instructor = instructor;
    }

    public List<StudentExternal> getStudents() {
        return students;
    }

    public void setStudents(List<StudentExternal> students) {
        this.students = students;
    }
}
