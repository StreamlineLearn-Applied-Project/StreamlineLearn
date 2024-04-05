package com.StreamlineLearn.DiscussionService.controller;

import com.StreamlineLearn.DiscussionService.model.Discussion;
import com.StreamlineLearn.DiscussionService.service.DiscussionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/discussions")
public class DiscussionController {
    private final DiscussionService discussionService;

    public DiscussionController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    // Get all discussions for a specific course
    @GetMapping
    public ResponseEntity<List<Discussion>> getAllDiscussions(@PathVariable Long courseId) {
        List<Discussion> discussions = discussionService.getAllDiscussions(courseId);
        return ResponseEntity.ok().body(discussions);
    }


    // Update an existing discussion in a specific course
    @PutMapping("/{discussionId}")
    public ResponseEntity<Discussion> updateDiscussion(@PathVariable Long courseId,
                                                       @PathVariable Long discussionId,
                                                       @RequestBody Discussion discussion) {
        Discussion updatedDiscussion = discussionService.updateDiscussion(courseId, discussionId, discussion);
        return ResponseEntity.ok().body(updatedDiscussion);
    }

    // Delete a discussion from a specific course
    @DeleteMapping("/{discussionId}")
    public ResponseEntity<?> deleteDiscussion(@PathVariable Long courseId,
                                              @PathVariable Long discussionId) {
        discussionService.deleteDiscussion(courseId, discussionId);
        return ResponseEntity.noContent().build();
    }
}
