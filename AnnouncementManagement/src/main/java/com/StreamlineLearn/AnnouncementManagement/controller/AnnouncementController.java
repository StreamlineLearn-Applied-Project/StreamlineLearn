package com.StreamlineLearn.AnnouncementManagement.controller;

import com.StreamlineLearn.AnnouncementManagement.dto.AnnouncementDto;
import com.StreamlineLearn.AnnouncementManagement.model.Announcement;
import com.StreamlineLearn.AnnouncementManagement.service.AnnouncementService;
import com.StreamlineLearn.SharedModule.annotation.IsInstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/courses/{courseId}/announcements")
@CrossOrigin(origins = "*")
public class AnnouncementController {
    // This declares a dependency on a AnnouncementService.
    private final AnnouncementService announcementService;

    // This is a constructor-based dependency injection of the AnnouncementService.
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping //HTTP POST requests onto the createCourse method.
    @IsInstructor // This is a custom annotation, presumably checking if the authenticated user is an instructor.
    public ResponseEntity<Announcement> createAnnouncement(@PathVariable Long courseId,
                                                     @Valid @RequestBody Announcement announcement,
                                                     @RequestHeader("Authorization") String authorizationHeader){

        // calls the createAnnouncement method in the AnnouncementService with the provided Announcement and authorization header.
        Announcement createdAnnouncement = announcementService.createAnnouncement(courseId, announcement, authorizationHeader );

        // This returns a ResponseEntity with the created Announcement and an HTTP status code
        // indicating that the Announcement was successfully created.
        return new ResponseEntity<>(createdAnnouncement, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<Announcement>> getAnnouncementByCourseId(@PathVariable Long courseId){
        Set<Announcement> announcements = announcementService.getAnnouncementsByCourseId(courseId);
        if(announcements != null && !announcements.isEmpty()){
            return new ResponseEntity<>(announcements, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{announcementId}")
    public ResponseEntity<AnnouncementDto> getAnnouncementById(@PathVariable Long courseId,
                                                            @PathVariable Long announcementId,
                                                            @RequestHeader("Authorization") String authorizationHeader){
        Optional<AnnouncementDto> announcement = announcementService.getAnnouncementById(courseId, announcementId, authorizationHeader);
        return announcement.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{announcementId}")
    @IsInstructor
    public ResponseEntity<String> updateAnnouncementById(@PathVariable Long courseId,
                                                         @PathVariable Long announcementId,
                                                         @RequestBody Announcement announcement,
                                                         @RequestHeader("Authorization") String authorizationHeader){
        boolean announcementUpdated = announcementService.updateAnnouncementById(courseId, announcementId, announcement, authorizationHeader);
        if(announcementUpdated){
            return new ResponseEntity<>("Announcement updated Successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Announcement not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{announcementId}")
    @IsInstructor
    public ResponseEntity<String> deleteAnnouncementById(@PathVariable Long courseId,
                                                         @PathVariable Long announcementId,
                                                         @RequestHeader("Authorization") String authorizationHeader){
        boolean announcementDeleted = announcementService.deleteAnnouncementById(courseId, announcementId, authorizationHeader);
        if(announcementDeleted){
            return new ResponseEntity<>("Announcement deleted Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Announcement not found", HttpStatus.NOT_FOUND);
        }
    }

}

