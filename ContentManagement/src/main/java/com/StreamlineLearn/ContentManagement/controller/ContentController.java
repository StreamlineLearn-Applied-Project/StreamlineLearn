package com.StreamlineLearn.ContentManagement.controller;

import com.StreamlineLearn.ContentManagement.model.Content;
import com.StreamlineLearn.ContentManagement.service.ContentService;
import com.StreamlineLearn.SharedModule.annotation.IsInstructor;
import com.StreamlineLearn.SharedModule.annotation.IsStudentOrInstructor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
                                                 @RequestPart("content") String contentJson,
                                                 @RequestPart("media") MultipartFile file,
                                                 @RequestHeader("Authorization") String authorizationHeader)
            throws JsonProcessingException {

        // Convert JSON string to a Content object
        Content content = new ObjectMapper().readValue(contentJson, Content.class);

        // calls the createContent method in the contentService.
        Content createdContent = contentService.createContent(courseId, content, file, authorizationHeader );

        // This returns a ResponseEntity with the created Content and an HTTP status code
        // indicating that the Content was successfully created.
        return new ResponseEntity<>(createdContent, HttpStatus.CREATED);
    }

    // Endpoint to get all contents for a course
    @GetMapping //HTTP GET requests onto the getContentByCourseId method.
    @IsStudentOrInstructor// This is a custom annotation, presumably checking if the authenticated user is an instructor or student.
    public ResponseEntity<Set<Content>> getContentsByCourseId(@PathVariable Long courseId,
                                                              @RequestHeader("Authorization") String authorizationHeader){

        Set<Content> contents = contentService.getContentsByCourseId(courseId,authorizationHeader);
        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

    // Endpoint to get a specific content by ID
    @GetMapping("/{contentId}")
    @IsStudentOrInstructor // This is a custom annotation, presumably checking if the authenticated user is an instructor or student.
    public ResponseEntity<Content> getContentById(@PathVariable Long courseId,
                                                  @PathVariable Long contentId,
                                                  @RequestHeader("Authorization") String authorizationHeader){

        // Call the service method to get the content by ID
        Optional<Content> content = contentService.getContentById(courseId,contentId, authorizationHeader);

        // If the content is present, return it with HTTP status code OK; otherwise, return 404
        return content.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<?> getContentMedia(@PathVariable Long courseId,
                                                         @PathVariable String fileName,
                                                         @RequestHeader("Authorization") String authorizationHeader) throws IOException {
        byte[] imageData=contentService.getContentMedia(courseId, fileName, authorizationHeader);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }

    // Endpoint to update a content by ID
    @PutMapping(value = "/{contentId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @IsInstructor // Custom annotation to check if the user is an instructor
    public ResponseEntity<String> updateContentById(@PathVariable Long courseId,
                                                    @PathVariable Long contentId,
                                                    @RequestPart("content") String contentJson,
                                                    @RequestPart(value = "media", required = false) MultipartFile file,
                                                    @RequestHeader("Authorization") String authorizationHeader) throws JsonProcessingException {

        // Convert JSON string to a Content object
        Content content = new ObjectMapper().readValue(contentJson, Content.class);

        // Call the service method to update the content
        boolean contentUpdated = contentService.updateContentById(courseId, contentId, content, file, authorizationHeader);

        // If the content was updated successfully, return a success message; otherwise, return 404
        return contentUpdated ? ResponseEntity.ok("Content updated successfully") : ResponseEntity.notFound().build();
    }


    // Endpoint to delete a content by ID
    @DeleteMapping("/{contentId}")
    @IsInstructor // Custom annotation to check if the user is an instructor
    public ResponseEntity<String> deleteContentById(@PathVariable Long courseId,
                                                    @PathVariable Long contentId,
                                                    @RequestHeader("Authorization") String authorizationHeader){

        // Call the service method to delete the content
        boolean contentDeleted = contentService.deleteContentById(courseId , contentId, authorizationHeader);
        // If the content was deleted successfully, return a success message; otherwise, return 404
        return contentDeleted ? ResponseEntity.ok("Content deleted Successfully") :
                ResponseEntity.notFound().build();
    }

}

