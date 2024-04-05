package com.StreamlineLearn.DiscussionService.repository;

import com.StreamlineLearn.DiscussionService.model.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
}
