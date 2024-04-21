package com.StreamlineLearn.AssessmentManagement.serviceImplementation;

import com.StreamlineLearn.AssessmentManagement.jwtUtil.JwtService;
import com.StreamlineLearn.AssessmentManagement.model.Assessment;
import com.StreamlineLearn.AssessmentManagement.model.Course;
import com.StreamlineLearn.AssessmentManagement.repository.AssessmentRepository;
import com.StreamlineLearn.AssessmentManagement.repository.CourseRepository;
import com.StreamlineLearn.AssessmentManagement.service.AssessmentService;
import com.StreamlineLearn.AssessmentManagement.service.CourseService;

import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class AssessmentServiceImplementation implements AssessmentService {
    private final JwtService jwtService;
    private final CourseService courseService;
    private final AssessmentRepository assessmentRepository;
    private final CourseRepository courseRepository;
    public AssessmentServiceImplementation(JwtService jwtService,
                                           CourseService courseService,
                                           AssessmentRepository assessmentRepository, CourseRepository courseRepository) {

        this.jwtService = jwtService;
        this.courseService = courseService;
        this.assessmentRepository = assessmentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void createAssessment(Long courseId,
                                   Assessment assessment,
                                   String authorizationHeader) {

        Course course = courseService.getCourseByCourseId(courseId);

        assessment.setCourse(course);

        assessmentRepository.save(assessment);

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

    @Override
    public Set<Assessment> getAssessmentByCourseId(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(null);
        return course.getAssessments();
    }
}
