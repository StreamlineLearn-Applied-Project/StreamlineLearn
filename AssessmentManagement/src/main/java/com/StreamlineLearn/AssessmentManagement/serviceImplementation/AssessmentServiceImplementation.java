package com.StreamlineLearn.AssessmentManagement.serviceImplementation;

import com.StreamlineLearn.AssessmentManagement.model.Assessment;
import com.StreamlineLearn.AssessmentManagement.model.Course;
import com.StreamlineLearn.AssessmentManagement.repository.AssessmentRepository;
import com.StreamlineLearn.AssessmentManagement.service.AssessmentService;
import com.StreamlineLearn.AssessmentManagement.service.CourseService;
import com.StreamlineLearn.AssessmentManagement.utility.JwtService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AssessmentServiceImplementation implements AssessmentService {
    private final JwtService jwtService;
    private final CourseService courseService;
    private final AssessmentRepository assessmentRepository;
    public AssessmentServiceImplementation(JwtService jwtService,
                                           CourseService courseService,
                                           AssessmentRepository assessmentRepository) {

        this.jwtService = jwtService;
        this.courseService = courseService;
        this.assessmentRepository = assessmentRepository;
    }

    @Override
    public String createAssessment(Long courseId,
                                   Assessment assessment,
                                   String authorizationHeader) {


        Course course = courseService.getCourseByCourseId(courseId);

        return null;
    }

    @Override
    public Assessment getAssessmentById(Long id) {
        return null;
    }


    @Override
    public boolean updateAssessmentById(Long id, Assessment assessment) {
        return false;
    }

    @Override
    public boolean deleteAssessmentById(Long id) {
        return false;
    }
}
