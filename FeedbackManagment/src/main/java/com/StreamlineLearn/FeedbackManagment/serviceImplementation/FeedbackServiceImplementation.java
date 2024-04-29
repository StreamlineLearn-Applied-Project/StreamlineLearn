package com.StreamlineLearn.FeedbackManagment.serviceImplementation;

import com.StreamlineLearn.FeedbackManagment.exception.FeedbackCreationException;
import com.StreamlineLearn.FeedbackManagment.model.Course;
import com.StreamlineLearn.FeedbackManagment.model.Feedback;
import com.StreamlineLearn.FeedbackManagment.model.Student;
import com.StreamlineLearn.FeedbackManagment.repository.FeedbackRepository;
import com.StreamlineLearn.FeedbackManagment.service.CourseService;
import com.StreamlineLearn.FeedbackManagment.service.FeedbackService;
import com.StreamlineLearn.FeedbackManagment.service.StudentService;
import com.StreamlineLearn.SharedModule.jwtUtil.SharedJwtService;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImplementation implements FeedbackService {

    private final CourseService courseService;
    private final SharedJwtService sharedJwtService;
    private final StudentService studentService;
    private final FeedbackRepository feedbackRepository;
    private static final int TOKEN_PREFIX_LENGTH = 7;

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImplementation.class);

    public FeedbackServiceImplementation(CourseService courseService,
                                         SharedJwtService sharedJwtService,
                                         StudentService studentService,
                                         FeedbackRepository feedbackRepository) {
        this.courseService = courseService;
        this.sharedJwtService = sharedJwtService;
        this.studentService = studentService;
        this.feedbackRepository = feedbackRepository;
    }

    private void validateStudentAndCourse(Long userId, Long courseId, String role) {
        if (!"STUDENT".equals(role) || !courseService.isStudentEnrolled(userId, courseId)) {
            throw new IllegalArgumentException("Only enrolled students can post feedback for the course");
        }
    }

    @Override
    public Feedback createFeedback(Long courseId, Feedback feedback, String authorizationHeader) {
        try {
            // Extract a role and studentId from the authorization header
            String role = sharedJwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
            Long userId = sharedJwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

            // Validate the student and course
            validateStudentAndCourse(userId, courseId, role);

            // Set the course and student to the feedback and save it
            feedback.setCourse(courseService.getCourseByCourseId(courseId));
            feedback.setStudent(studentService.findStudentByStudentId(userId));
            return feedbackRepository.save(feedback);
        } catch (IllegalArgumentException e) {
            // Handle known exceptions such as invalid arguments
            throw e;
        } catch (Exception ex) {
            // Log and handle unexpected exceptions
            logger.error("Error creating feedback", ex);
            throw new FeedbackCreationException("Unable to create feedback: " + ex.getMessage());
        }
    }


    @Override
    public List<Feedback> getAllFeedbacks(Long courseId) {

        return feedbackRepository.findByCourseId(courseId);
    }

    @Override
    public boolean updateFeedback(Long courseId, Long feedbackId, Feedback updatedFeedback, String authorizationHeader) {
        Long studentId = sharedJwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Student student = studentService.findStudentByStudentId(studentId);
        if (student != null) {
            Feedback existingFeedback = feedbackRepository.findById(feedbackId)
                    .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));

            // Ensure that the student attempting to update the feedback is the same as the one who created it
            if (existingFeedback.getStudent().getId().equals(studentId)) {
                // Update feedback
                existingFeedback.setFeedback(updatedFeedback.getFeedback());
                existingFeedback.setDate(updatedFeedback.getDate());
                existingFeedback.setRating(updatedFeedback.getRating());
                feedbackRepository.save(existingFeedback); // Update existing feedback, not the updatedFeedback
                return true;
            } else {
                throw new RuntimeException("Student is not enrolled in the course associated with this feedback");
            }
        } else {
            throw new RuntimeException("Only students are authorized to update feedbacks");
        }
    }


    @Override
    public void deleteFeedback(Long courseId, Long feedbackId, String authorizationHeader) {
        Long roleId = sharedJwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));

        // Check if the student attempting to delete the feedback is the same as the one who created it, or if they are an admin
        if (feedback.getStudent().getId().equals(roleId) || courseService.isInstructorOfCourse(roleId, courseId)) {
            feedbackRepository.deleteById(feedbackId);
        } else {
            throw new RuntimeException("You are not authorized to delete this feedback");
        }
    }
}
