package com.StreamlineLearn.AssessmentManagement.controller;

import com.StreamlineLearn.AssessmentManagement.model.Assessment;
import com.StreamlineLearn.AssessmentManagement.model.Course;
import com.StreamlineLearn.AssessmentManagement.model.InsertionData;
import com.StreamlineLearn.AssessmentManagement.model.Student;
import com.StreamlineLearn.AssessmentManagement.repository.AssessmentRepository;
import com.StreamlineLearn.AssessmentManagement.repository.CourseRepository;
import com.StreamlineLearn.AssessmentManagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataInsertionController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/insertData")
    public String insertData(@RequestBody InsertionData insertionData) {
        // Creating Course
        Course course = new Course();
        course.setTitle(insertionData.getCourseTitle());
        courseRepository.save(course);

        // Creating Assessment
        Assessment assessment = new Assessment();
        assessment.setTitle(insertionData.getAssessmentTitle());
        assessment.setPercentage(insertionData.getAssessmentPercentage());
        assessment.setCourse(course); // Associate with Course
        assessmentRepository.save(assessment);

        // Creating Students
        for (String studentName : insertionData.getStudentNames()) {
            Student student = new Student();
            student.setUserName(studentName);
            student.getAssessments().add(assessment); // Associate with Assessment
            studentRepository.save(student);
        }

        return "Data inserted successfully!";
    }
}

