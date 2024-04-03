package com.StreamlineLearn.AssessmentManagement.serviceImplementation;

import com.StreamlineLearn.AssessmentManagement.external.CourseExternal;
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
    private final CourseManagementServiceClientImplementation courseManagementServiceClientImplementation;
    private final JwtService jwtService;
    private final CourseService courseService;
    private final AssessmentRepository assessmentRepository;
    public AssessmentServiceImplementation(CourseManagementServiceClientImplementation courseManagementServiceClientImplementation,
                                           JwtService jwtService,
                                           CourseService courseService,
                                           AssessmentRepository assessmentRepository) {
        this.courseManagementServiceClientImplementation = courseManagementServiceClientImplementation;
        this.jwtService = jwtService;
        this.courseService = courseService;
        this.assessmentRepository = assessmentRepository;
    }

    @Override
    public String createAssessment(Long courseId,
                                   Assessment assessment,
                                   String authorizationHeader) {
        CourseExternal externalCourse = courseManagementServiceClientImplementation.getCourseById(courseId);

        Course course = courseService.getCourseByCourseId(courseId);

        if(course!= null) {
            assessment.setCourse(course);

            assessmentRepository.save(assessment);

        } else {
            if (Objects.equals(externalCourse.getId(), courseId) &&
                    Objects.equals(externalCourse.getInstructor().getInstructorId(),
                            jwtService.extractRoleId(authorizationHeader))) {

                    Course newCourse = courseService.createCourse(externalCourse);

                    assessment.setCourse(newCourse);

                    assessmentRepository.save(assessment);
                return "InstructorExternal Authorized to Add Assessments";
            }
        }

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
