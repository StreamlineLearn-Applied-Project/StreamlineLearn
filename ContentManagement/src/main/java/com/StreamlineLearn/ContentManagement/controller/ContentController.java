package com.StreamlineLearn.ContentManagement.controller;

import com.StreamlineLearn.ContentManagement.dto.ContentDto;
import com.StreamlineLearn.ContentManagement.model.Content;
import com.StreamlineLearn.ContentManagement.service.ContentService;
import com.StreamlineLearn.SharedModule.annotation.IsInstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/courses/{courseId}/contents")
@CrossOrigin(origins = "*")
public class ContentController {
    // This declares a dependency on a ContentService.
    private final ContentService contentService;

    // This is a constructor-based dependency injection of the ContentService.
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @PostMapping //HTTP POST requests onto the createContent method.
    @IsInstructor // This is a custom annotation, presumably checking if the authenticated user is an instructor.
    public ResponseEntity<Content> createContent(@PathVariable Long courseId,
                                                @Valid @RequestBody Content content,
                                                @RequestHeader("Authorization") String authorizationHeader){

        // calls the createContent method in the contentService.
        Content createdContent = contentService.createContent(courseId, content, authorizationHeader );

        // This returns a ResponseEntity with the created Content and an HTTP status code
        // indicating that the Content was successfully created.
        return new ResponseEntity<>(createdContent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<Content>> getContentsByCourseId(@PathVariable Long courseId){
        Set<Content> contents = contentService.getContentsByCourseId(courseId);
        if(contents != null && !contents.isEmpty()){
            return new ResponseEntity<>(contents, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<ContentDto> getContentById(@PathVariable Long courseId,
                                                  @PathVariable Long contentId,
                                                  @RequestHeader("Authorization") String authorizationHeader){

        Optional<ContentDto> content = contentService.getContentById(courseId,contentId, authorizationHeader);

        return content.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{contentId}")
    @IsInstructor
    public ResponseEntity<String> updateContentById(@PathVariable Long courseId, @PathVariable Long contentId, @RequestBody Content content,
                                                    @RequestHeader("Authorization") String authorizationHeader){
        boolean contentUpdated = contentService.updateContentById(courseId ,contentId, content, authorizationHeader);
        if(contentUpdated){
            return new ResponseEntity<>("Content updated Successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Content not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{contentId}")
    @IsInstructor
    public ResponseEntity<String> deleteContentById(@PathVariable Long courseId, @PathVariable Long contentId, @RequestHeader("Authorization") String authorizationHeader){
        boolean contentDeleted = contentService.deleteContentById(courseId , contentId, authorizationHeader);
        if(contentDeleted){
            return new ResponseEntity<>("Content deleted Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Content not found", HttpStatus.NOT_FOUND);
        }
    }

}

