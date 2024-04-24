package com.StreamlineLearn.FeedbackManagment.controller;

import com.StreamlineLearn.FeedbackManagment.model.Feedback;
import com.StreamlineLearn.FeedbackManagment.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;

    }

    @PostMapping
    public ResponseEntity<String> createFeedback(@PathVariable Long courseId,
                                                 @RequestBody Feedback feedback,
                                                 @RequestHeader("Authorization") String authorizationHeader){

        feedbackService.createFeedback(courseId, feedback, authorizationHeader );

        return new ResponseEntity<>("Feedback added to the course", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks(@PathVariable Long courseId) {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks(courseId);
        return ResponseEntity.ok().body(feedbacks);
    }

    @PutMapping("/{feedbackId}")
    public ResponseEntity<?> updateFeedback(@PathVariable Long courseId,
                                            @PathVariable Long feedbackId,
                                            @RequestBody Feedback feedback,
                                            @RequestHeader("Authorization") String authorizationHeader) {
        feedbackService.updateFeedback(courseId, feedbackId, feedback, authorizationHeader);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long courseId,
                                            @PathVariable Long feedbackId,
                                            @RequestHeader("Authorization") String authorizationHeader) {
        feedbackService.deleteFeedback(courseId, feedbackId, authorizationHeader);
        return ResponseEntity.noContent().build();
    }
}
