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
@CrossOrigin(origins = "*")
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
    @IsStudentOrInstructor// This is a custom annotation, presumably checking if the authenticated user is an instructor.
    public ResponseEntity<Discussion> addThread(@PathVariable Long courseId,
                                                @PathVariable Long discussionId,
                                                @RequestBody String thread,
                                                @RequestHeader("Authorization") String authorizationHeader) {
        Discussion updatedDiscussion = discussionService.addThread(courseId, discussionId, thread, authorizationHeader);
        return ResponseEntity.ok().body(updatedDiscussion);
    }

    // Get all discussions for a specific course
    @GetMapping //HTTP GET requests onto the getDiscussionByCourseId method.
    @IsStudentOrInstructor
    public ResponseEntity<List<Discussion>> getAllDiscussions(@PathVariable Long courseId,
                                                              @RequestHeader("Authorization") String authorizationHeader) {

        // Call the service method to get all the Discussions of a course.
        List<Discussion> discussions = discussionService.getAllDiscussions(courseId,authorizationHeader);
        return ResponseEntity.ok().body(discussions);
    }

    // Get discussion by courseId and DiscussionId
    @GetMapping("/{discussionId}") //HTTP GET requests onto the getDiscussionByCourseId method.
    @IsStudentOrInstructor
    public ResponseEntity<Discussion> getDiscussionByDiscussionId(@PathVariable Long courseId,
                                                                  @PathVariable Long discussionId,
                                                              @RequestHeader("Authorization") String authorizationHeader) {

        // Call the service method to get all the Discussions of a course.
        Discussion discussion = discussionService.getDiscussionById(courseId,discussionId,authorizationHeader);
        return ResponseEntity.ok().body(discussion);
    }


    // Update an existing discussion in a specific course
    @PutMapping("/{discussionId}")
    @IsStudentOrInstructor// This is a custom annotation, presumably checking if the authenticated user is an instructor.
    public ResponseEntity<?> updateDiscussion(@PathVariable Long courseId,
                                                       @PathVariable Long discussionId,
                                                       @Valid @RequestBody Discussion discussion,
                                                       @RequestHeader("Authorization") String authorizationHeader) {

        // Call the service method to update the Discussion
        Boolean updatedDiscussion = discussionService.updateDiscussion(courseId, discussionId, discussion, authorizationHeader);

        // If the Discussion was updated successfully, return a success message; otherwise, return 404
        return updatedDiscussion ? ResponseEntity.ok("Discussion updated Successfully") :
                ResponseEntity.notFound().build();
    }

    // Delete a discussion from a specific course
    @DeleteMapping("/{discussionId}")
    @IsStudentOrInstructor// This is a custom annotation, presumably checking if the authenticated user is an instructor.
    public ResponseEntity<?> deleteDiscussion(@PathVariable Long courseId,
                                              @PathVariable Long discussionId,
                                              @RequestHeader("Authorization") String authorizationHeader) {

        // Call the service method to Delete the Discussion
        Boolean isDeleted = discussionService.deleteDiscussion(courseId, discussionId, authorizationHeader);

        // If the Discussion was deleted successfully, return a success message; otherwise, return 404
        return isDeleted ? ResponseEntity.ok("Discussion updated Successfully") :
                ResponseEntity.notFound().build();
    }
}
