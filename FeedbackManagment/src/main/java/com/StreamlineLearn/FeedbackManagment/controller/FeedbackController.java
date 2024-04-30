package com.StreamlineLearn.FeedbackManagment.controller;

import com.StreamlineLearn.FeedbackManagment.model.Feedback;
import com.StreamlineLearn.FeedbackManagment.service.FeedbackService;
import com.StreamlineLearn.SharedModule.annotation.IsStudent;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses/{courseId}/feedback")
public class FeedbackController {
    // This declares a dependency on a FeedbackService.
    private final FeedbackService feedbackService;

    // This is a constructor-based dependency injection of the FeedbackService.
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;

    }

    @PostMapping //HTTP POST requests onto the createCourse method.
    @IsStudent // This is a custom annotation, presumably checking if the authenticated user is an instructor.
    public ResponseEntity<Feedback> createFeedback(@PathVariable Long courseId,
                                                 @Valid @RequestBody Feedback feedback,
                                                 @RequestHeader("Authorization") String authorizationHeader){

        Feedback createdFeedback = feedbackService.createFeedback(courseId, feedback, authorizationHeader );

        return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
    }

    // Endpoint to get all Feedbacks for a course
    @GetMapping //HTTP GET requests onto the getFeedbackByCourseId method.
    public ResponseEntity<Optional<List<Feedback>>> getAllFeedbacks(@PathVariable Long courseId) {

        Optional<List<Feedback>> feedbacks = feedbackService.getAllFeedbacks(courseId);
        return ResponseEntity.ok().body(feedbacks);
    }

    // Endpoint to update a Feedback by ID
    @PutMapping("/{feedbackId}")
    @IsStudent
    public ResponseEntity<?> updateFeedback(@PathVariable Long courseId, @PathVariable Long feedbackId,
                                            @Valid @RequestBody Feedback feedback,
                                            @RequestHeader("Authorization") String authorizationHeader) {

        // Call the service method to update the Feedback
        boolean feedbackUpdated = feedbackService
                .updateFeedback(courseId, feedbackId, feedback, authorizationHeader);

        // If the Feedback was updated successfully, return a success message; otherwise, return 404
        return feedbackUpdated ? ResponseEntity.ok("Feedback updated Successfully") :
                ResponseEntity.notFound().build();
    }

    // Endpoint to delete a Feedback by ID
    @DeleteMapping("/{feedbackId}")
    @IsStudent
    public ResponseEntity<?> deleteFeedback(@PathVariable Long courseId,
                                            @PathVariable Long feedbackId,
                                            @RequestHeader("Authorization") String authorizationHeader) {

        boolean feedbackDeleted = feedbackService.deleteFeedback(courseId, feedbackId, authorizationHeader);
        // If the Feedback was deleted successfully, return a success message; otherwise, return 404
        return feedbackDeleted ? ResponseEntity.ok("Feedback deleted Successfully") :
                ResponseEntity.notFound().build();
    }
}
