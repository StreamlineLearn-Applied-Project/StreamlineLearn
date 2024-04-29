package com.StreamlineLearn.FeedbackManagment.service;

import com.StreamlineLearn.FeedbackManagment.model.Feedback;

import java.util.List;

public interface FeedbackService {
    Feedback createFeedback(Long courseId, Feedback feedback, String authorizationHeader);

    List<Feedback> getAllFeedbacks(Long courseId);

    boolean updateFeedback(Long courseId, Long feedbackId, Feedback feedback, String authorizationHeader);

    void deleteFeedback(Long courseId, Long feedbackId, String authorizationHeader);
}
