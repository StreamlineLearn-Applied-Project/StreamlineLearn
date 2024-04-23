package com.StreamlineLearn.DiscussionService.repository;

import com.StreamlineLearn.DiscussionService.model.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    List<Discussion> findByCourseId(Long courseId);
}
