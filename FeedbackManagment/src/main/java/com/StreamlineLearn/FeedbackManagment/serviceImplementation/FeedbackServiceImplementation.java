package com.StreamlineLearn.FeedbackManagment.serviceImplementation;

import com.StreamlineLearn.FeedbackManagment.exception.FeedbackException;
import com.StreamlineLearn.FeedbackManagment.model.Feedback;
import com.StreamlineLearn.FeedbackManagment.model.Student;
import com.StreamlineLearn.FeedbackManagment.repository.FeedbackRepository;
import com.StreamlineLearn.FeedbackManagment.service.CourseService;
import com.StreamlineLearn.FeedbackManagment.service.FeedbackService;
import com.StreamlineLearn.FeedbackManagment.service.StudentService;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.StreamlineLearn.SharedModule.service.JwtUserService;
import com.StreamlineLearn.SharedModule.sharedException.CourseNotFoundException;
import com.StreamlineLearn.SharedModule.sharedException.StudentNotFoundException;
import com.StreamlineLearn.SharedModule.sharedException.UnauthorizedException;
import jakarta.transaction.Transactional;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImplementation implements FeedbackService {
    private final CourseService courseService;
    private final StudentService studentService;
    private final FeedbackRepository feedbackRepository;
    private final JwtUserService jwtUserService;
    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImplementation.class);

    // Constructor injection for dependencies
    public FeedbackServiceImplementation(CourseService courseService,
                                         StudentService studentService,
                                         FeedbackRepository feedbackRepository, JwtUserService jwtUserService) {
        this.courseService = courseService;
        this.jwtUserService = jwtUserService;
        this.studentService = studentService;
        this.feedbackRepository = feedbackRepository;
    }

    // Helper method to retrieve feedback by ID
    private Feedback getFeedbackById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));
    }

    // Method to create feedback
    @Override
    public Feedback createFeedback(Long courseId, Feedback feedback, String authorizationHeader) {
        try {
            // Extract user information from the authorization header
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

            // Check if the user is a student
            if ("STUDENT".equals(userSharedDto.getRole())) {
                // Check if the student is enrolled in the course
                if (courseService.isStudentEnrolled(userSharedDto.getId(), courseId)) {
                    // Set the course and student to the feedback and save it
                    feedback.setCourse(courseService.getCourseByCourseId(courseId));
                    feedback.setStudent(studentService.findStudentByStudentId(userSharedDto.getId()));

                    return feedbackRepository.save(feedback);
                } else {
                    throw new StudentNotFoundException("Student is not enrolled in the course.");
                }
            } else {
                throw new UnauthorizedException("Only students can create feedback.");
            }
        } catch (UnauthorizedException ex) {
            // Handle unauthorized access
            logger.warn(ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            // Log and handle unexpected exceptions
            logger.error("Error creating feedback", ex);
            throw new FeedbackException("Unable to create feedback: " + ex.getMessage());
        }
    }

    // Method to get all feedbacks for a course
    @Override
    public Optional<List<Feedback>> getAllFeedbacks(Long courseId) {
        try {
            // Check if the course exists
            if (courseService.getCourseByCourseId(courseId) == null) {
                throw new CourseNotFoundException("Course with ID " + courseId + " not found.");
            }
            // Retrieve feedbacks for the course
            List<Feedback> feedbacks = feedbackRepository.findByCourseId(courseId);
            return Optional.ofNullable(feedbacks);

        } catch (Exception ex) {
            // Log and handle exceptions
            logger.error("Error getting all feedbacks", ex);
            throw new FeedbackException("Unable to retrieve feedbacks: " + ex.getMessage());
        }
    }

    // Method to update feedback
    @Override
    @Transactional
    public boolean updateFeedback(Long courseId, Long feedbackId, Feedback updatedFeedback, String authorizationHeader) {
        try {
            // Extract user information from the authorization header
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);
            // Find the student by ID
            Student student = studentService.findStudentByStudentId(userSharedDto.getId());

            if (student == null) {
                // Throw exception if the student not found
                throw new StudentNotFoundException("Student not found");
            }
            // Retrieve existing feedback by ID
            Feedback existingFeedback = getFeedbackById(feedbackId);

            if (!existingFeedback.getStudent().getId().equals(userSharedDto.getId())) {
                // Check if the student attempting to delete the feedback is the same as the one who created it
                throw new UnauthorizedException("Student is not authorized to update this feedback");
            }
            // Update feedback
            existingFeedback.setFeedback(updatedFeedback.getFeedback());
            existingFeedback.setDate(updatedFeedback.getDate());
            existingFeedback.setRating(updatedFeedback.getRating());
            feedbackRepository.save(existingFeedback);

            return true;

        } catch (Exception ex) {
            // Log and handle exceptions
            logger.error("Error updating feedback", ex);
            throw new FeedbackException("Unable to update feedback: " + ex.getMessage());
        }
    }

    @Override
    public boolean deleteFeedback(Long courseId, Long feedbackId, String authorizationHeader) {
        try {
            // Extract user information from the authorization header
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);
            // Retrieve existing feedback by ID
            Feedback existingFeedback = getFeedbackById(feedbackId);

            // Check if the student attempting to delete the feedback is the same as the one who created it.
            if (existingFeedback.getStudent().getId().equals(userSharedDto.getId()) ||
                    courseService.isInstructorOfCourse(userSharedDto.getId(), courseId)) {
                // Delete feedback
                feedbackRepository.deleteById(feedbackId);

                return true;
            } else {
                throw new UnauthorizedException("You are not authorized to delete this feedback");
            }
        } catch (Exception ex) {
            // Log and handle exceptions
            logger.error("Error deleting feedback", ex);
            throw new FeedbackException("Unable to delete feedback: " + ex.getMessage());
        }
    }
}
