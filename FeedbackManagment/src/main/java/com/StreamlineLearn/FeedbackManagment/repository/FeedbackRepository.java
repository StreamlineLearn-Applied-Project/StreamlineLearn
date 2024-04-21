package com.StreamlineLearn.FeedbackManagment.repository;

import com.StreamlineLearn.FeedbackManagment.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
