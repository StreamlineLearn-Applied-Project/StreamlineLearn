package com.StreamlineLearn.FeedbackManagment.controller;

import com.StreamlineLearn.FeedbackManagment.model.Feedback;
import com.StreamlineLearn.FeedbackManagment.service.FeedbackService;
import com.StreamlineLearn.SharedModule.annotation.IsStudent;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks(@PathVariable Long courseId) {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks(courseId);
        return ResponseEntity.ok().body(feedbacks);
    }

    @PutMapping("/{feedbackId}")
    @IsStudent
    public ResponseEntity<?> updateFeedback(@PathVariable Long courseId,
                                            @PathVariable Long feedbackId,
                                            @RequestBody Feedback feedback,
                                            @RequestHeader("Authorization") String authorizationHeader) {
        feedbackService.updateFeedback(courseId, feedbackId, feedback, authorizationHeader);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{feedbackId}")
    @IsStudent
    public ResponseEntity<?> deleteFeedback(@PathVariable Long courseId,
                                            @PathVariable Long feedbackId,
                                            @RequestHeader("Authorization") String authorizationHeader) {
        feedbackService.deleteFeedback(courseId, feedbackId, authorizationHeader);
        return ResponseEntity.noContent().build();
    }
}
