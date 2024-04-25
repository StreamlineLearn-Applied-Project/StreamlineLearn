package com.StreamlineLearn.AssessmentSubmissionService.serviceImplementation;

import com.StreamlineLearn.AssessmentSubmissionService.model.Assessment;
import com.StreamlineLearn.AssessmentSubmissionService.model.Course;
import com.StreamlineLearn.AssessmentSubmissionService.model.Instructor;
import com.StreamlineLearn.AssessmentSubmissionService.repository.AssessmentRepository;
import com.StreamlineLearn.AssessmentSubmissionService.service.AssessmentService;
import com.StreamlineLearn.AssessmentSubmissionService.service.CourseService;
import com.StreamlineLearn.AssessmentSubmissionService.service.InstructorService;
import com.StreamlineLearn.SharedModule.dto.CourseAssessmentDto;
import jakarta.persistence.EntityNotFoundException;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AssessmentServiceImplementation implements AssessmentService {
    private final CourseService courseService;
    private final InstructorService instructorService;
    private final AssessmentRepository assessmentRepository;

    public AssessmentServiceImplementation(CourseService courseService,
                                           InstructorService instructorService,
                                           AssessmentRepository assessmentRepository) {
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.assessmentRepository = assessmentRepository;
    }

    public void saveAssessment(CourseAssessmentDto courseAssessmentDto) {
        try {
            // Retrieve the course and instructor from the database
            Course course = courseService.getCourseByCourseId(courseAssessmentDto.getCourseId());
            Instructor instructor = instructorService.findInstructorById(courseAssessmentDto.getInstructorId());

            // Create and save the assessment
            Assessment assessment = new Assessment();
            assessment.setCourse(course);
            // Set other assessment properties as needed
            assessmentRepository.save(assessment);
        } catch (EntityNotFoundException | IllegalArgumentException ex) {
            // Print the stack trace to the standard error stream
            ex.printStackTrace(System.err);

            // Handle the error as appropriate, e.g., throw a custom exception, return a message, etc.
            throw new RuntimeException("Error occurred while saving assessment: " + ex.getMessage());
        }
    }

    @Override
    public Assessment findAssessmentById(Long assessmentId) {
        return assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assessment not found with ID: " + assessmentId));
    }
}


