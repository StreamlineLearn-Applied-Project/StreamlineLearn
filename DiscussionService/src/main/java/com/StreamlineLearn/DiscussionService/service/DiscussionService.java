package com.StreamlineLearn.DiscussionService.service;

import com.StreamlineLearn.DiscussionService.model.Discussion;

import java.util.List;

public interface DiscussionService {
    List<Discussion> getAllDiscussions(Long courseId);

    Discussion updateDiscussion(Long courseId, Long discussionId, Discussion discussion);

    void deleteDiscussion(Long courseId, Long discussionId);
}
 