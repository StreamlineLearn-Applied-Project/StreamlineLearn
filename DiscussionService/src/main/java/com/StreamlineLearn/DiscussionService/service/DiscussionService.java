package com.StreamlineLearn.DiscussionService.service;

import com.StreamlineLearn.DiscussionService.model.Discussion;

import java.util.List;

public interface DiscussionService {
    void createDiscussion(Long courseId, Discussion discussion, String authorizationHeader);

    Discussion addThread(Long courseId, Long discussionId, String thread, String authorizationHeader);

    List<Discussion> getAllDiscussions(Long courseId, String authorizationHeader);

    Boolean updateDiscussion(Long courseId, Long discussionId, Discussion discussion, String authorizationHeader);

    Boolean deleteDiscussion(Long courseId, Long discussionId, String authorizationHeader);


}
 