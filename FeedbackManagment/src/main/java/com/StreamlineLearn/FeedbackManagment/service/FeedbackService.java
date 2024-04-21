package com.StreamlineLearn.FeedbackManagment.service;

import com.StreamlineLearn.FeedbackManagment.model.Feedback;

import java.util.List;

public interface FeedbackService {
    void createFeedback(Long courseId, Feedback feedback, String authorizationHeader);

    List<Feedback> getAllFeedbacks(Long courseId);

    Feedback updateFeedback(Long courseId, Long feedbackId, Feedback feedback);

    void deleteFeedbck(Long courseId, Long feedbackId);
}
