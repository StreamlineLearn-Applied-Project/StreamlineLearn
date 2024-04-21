package com.StreamlineLearn.DiscussionService.service;

import com.StreamlineLearn.DiscussionService.model.Discussion;

import java.util.List;

public interface DiscussionService {
    void createDiscussion(Long courseId, Discussion discussion, String authorizationHeader);

    List<Discussion> getAllDiscussions(Long courseId);

    Discussion updateDiscussion(Long courseId, Long discussionId, Discussion discussion);

    void deleteDiscussion(Long courseId, Long discussionId);
}
 