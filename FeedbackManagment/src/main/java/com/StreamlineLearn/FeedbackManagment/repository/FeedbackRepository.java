package com.StreamlineLearn.FeedbackManagment.repository;

import com.StreamlineLearn.FeedbackManagment.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByCourseId(Long courseId);
}

