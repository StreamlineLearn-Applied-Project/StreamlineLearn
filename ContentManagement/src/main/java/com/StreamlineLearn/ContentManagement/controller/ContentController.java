package com.StreamlineLearn.ContentManagement.controller;

import com.StreamlineLearn.ContentManagement.model.Content;
import com.StreamlineLearn.ContentManagement.service.ContentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/courses/{courseId}/contents")
@CrossOrigin(origins = "*")
public class ContentController {
    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @PostMapping
    public ResponseEntity<String> createContent(@PathVariable Long courseId,
                                                @RequestBody Content content,
                                                @RequestHeader("Authorization") String authorizationHeader){

        contentService.createContent(courseId, content, authorizationHeader );
        return new ResponseEntity<>("Content Added successfully.", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<Content>> getContentByCourseId(@PathVariable Long courseId){
        Set<Content> contents = contentService.getContentByCourseId(courseId);
        if(contents != null && !contents.isEmpty()){
            return new ResponseEntity<>(contents, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> getContentById(@PathVariable Long id){
        Content content = contentService.getContentById(id);
        if(content != null){
            return new ResponseEntity<>(content, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateContentById(@PathVariable Long id, @RequestBody Content content,
                                                    @RequestHeader("Authorization") String authorizationHeader){
        boolean contentUpdated = contentService.updateContentById(id, content);
        if(contentUpdated){
            return new ResponseEntity<>("Content updated Successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Content not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContentById(@PathVariable Long id){
        boolean contentDeleted = contentService.deleteContentById(id);
        if(contentDeleted){
            return new ResponseEntity<>("Content deleted Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Content not found", HttpStatus.NOT_FOUND);
        }
    }

}

