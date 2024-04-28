package com.StreamlineLearn.FeedbackManagment.serviceImplementation;

import com.StreamlineLearn.FeedbackManagment.model.Course;
import com.StreamlineLearn.FeedbackManagment.model.Feedback;
import com.StreamlineLearn.FeedbackManagment.model.Student;
import com.StreamlineLearn.FeedbackManagment.repository.FeedbackRepository;
import com.StreamlineLearn.FeedbackManagment.service.CourseService;
import com.StreamlineLearn.FeedbackManagment.service.FeedbackService;
import com.StreamlineLearn.FeedbackManagment.service.StudentService;
import com.StreamlineLearn.SharedModule.jwtUtil.SharedJwtService;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImplementation implements FeedbackService {

    private final CourseService courseService;
    private final SharedJwtService sharedJwtService;
    private final StudentService studentService;
    private final FeedbackRepository feedbackRepository;
    private static final int TOKEN_PREFIX_LENGTH = 7;

    public FeedbackServiceImplementation(CourseService courseService,
                                         SharedJwtService sharedJwtService,
                                         StudentService studentService,
                                         FeedbackRepository feedbackRepository) {
        this.courseService = courseService;
        this.sharedJwtService = sharedJwtService;
        this.studentService = studentService;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public void createFeedback(Long courseId, Feedback feedback, String authorizationHeader) {
        String role = sharedJwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long studentId = sharedJwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        Student student = studentService.findStudentByStudentId(studentId);
        Course course = courseService.getCourseByCourseId(courseId);

        // Check if the user is a student who enrolled in the course
        if ("STUDENT".equals(role) && courseService.isStudentEnrolled(studentId, courseId)) {
            feedback.setCourse(course);
            feedback.setStudent(student);
            feedbackRepository.save(feedback);
        }else {
            throw new RuntimeException("Only Students who are enrolled in this course can post feedback");
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
