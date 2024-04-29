package com.StreamlineLearn.DiscussionService.controller;

import com.StreamlineLearn.DiscussionService.model.Discussion;
import com.StreamlineLearn.DiscussionService.service.DiscussionService;
import com.StreamlineLearn.SharedModule.annotation.IsStudent;
import com.StreamlineLearn.SharedModule.annotation.IsStudentOrInstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/discussions")
public class DiscussionController {
    // This declares a dependency on a DiscussionService.
    private final DiscussionService discussionService;

    // This is a constructor-based dependency injection of the DiscussionService.
    public DiscussionController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @PostMapping //HTTP POST requests onto the createCourse method.
    @IsStudentOrInstructor// This is a custom annotation, presumably checking if the authenticated user is an instructor.
    public ResponseEntity<Discussion> createDiscussion(@PathVariable Long courseId,
                                                   @Valid @RequestBody Discussion discussion,
                                                   @RequestHeader("Authorization") String authorizationHeader){

        // calls the createCourse method in the CourseService with the provided Course and authorization header.
        Discussion createdDiscussion = discussionService.createDiscussion(courseId, discussion, authorizationHeader );

        return new ResponseEntity<>(createdDiscussion,HttpStatus.CREATED);
    }

    // Add a thread to an existing discussion
    @PostMapping("/{discussionId}/threads")
    public ResponseEntity<Discussion> addThread(@PathVariable Long courseId,
                                                @PathVariable Long discussionId,
                                                @RequestBody String thread,
                                                @RequestHeader("Authorization") String authorizationHeader) {
        Discussion updatedDiscussion = discussionService.addThread(courseId, discussionId, thread, authorizationHeader);
        return ResponseEntity.ok().body(updatedDiscussion);
    }

    // Get all discussions for a specific course
    @GetMapping
    public ResponseEntity<List<Discussion>> getAllDiscussions(@PathVariable Long courseId,
                                                              @RequestHeader("Authorization") String authorizationHeader) {
        List<Discussion> discussions = discussionService.getAllDiscussions(courseId,authorizationHeader);
        return ResponseEntity.ok().body(discussions);
    }


    // Update an existing discussion in a specific course
    @PutMapping("/{discussionId}")
    @IsStudent
    public ResponseEntity<Boolean> updateDiscussion(@PathVariable Long courseId,
                                                       @PathVariable Long discussionId,
                                                       @RequestBody Discussion discussion,
                                                       @RequestHeader("Authorization") String authorizationHeader) {
        Boolean updatedDiscussion = discussionService.updateDiscussion(courseId, discussionId, discussion, authorizationHeader);
        return ResponseEntity.ok().body(updatedDiscussion);
    }

    // Delete a discussion from a specific course
    @DeleteMapping("/{discussionId}")
    @IsStudent
    public ResponseEntity<?> deleteDiscussion(@PathVariable Long courseId,
                                              @PathVariable Long discussionId,
                                              @RequestHeader("Authorization") String authorizationHeader) {
        Boolean isDeleted = discussionService.deleteDiscussion(courseId, discussionId, authorizationHeader);
        return ResponseEntity.noContent().build();
    }
}
