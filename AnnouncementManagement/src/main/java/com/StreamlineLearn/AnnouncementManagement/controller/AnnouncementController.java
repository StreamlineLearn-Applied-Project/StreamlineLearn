package com.StreamlineLearn.AnnouncementManagement.controller;

import com.StreamlineLearn.AnnouncementManagement.dto.AnnouncementDto;
import com.StreamlineLearn.AnnouncementManagement.model.Announcement;
import com.StreamlineLearn.AnnouncementManagement.service.AnnouncementService;
import com.StreamlineLearn.SharedModule.annotation.IsInstructor;
import com.StreamlineLearn.SharedModule.annotation.IsStudentOrInstructor;
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
    // This declares a dependency on an AnnouncementService.
    private final AnnouncementService announcementService;

    // This is a constructor-based dependency injection of the AnnouncementService.
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    // Endpoint to create a new announcement
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

    // Endpoint to get all announcements for a course
    @GetMapping //HTTP GET requests onto the getAnnouncementByCourseId method.
    @IsStudentOrInstructor // This is a custom annotation, presumably checking if the authenticated user is an instructor or student.
    public ResponseEntity<Set<Announcement>> getAnnouncementByCourseId(@PathVariable Long courseId,
                                                                       @RequestHeader("Authorization") String authorizationHeader){

        Set<Announcement> announcements = announcementService.getAnnouncementsByCourseId(courseId, authorizationHeader);
        return new ResponseEntity<>(announcements, HttpStatus.OK);
    }

    // Endpoint to get a specific announcement by ID
    @GetMapping("/{announcementId}")
    @IsStudentOrInstructor // This is a custom annotation, presumably checking if the authenticated user is an instructor or student.
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable Long courseId,
                                                            @PathVariable Long announcementId,
                                                            @RequestHeader("Authorization") String authorizationHeader){

        // Call the service method to get the announcement by ID
        Optional<Announcement> announcement = announcementService
                .getAnnouncementById(courseId, announcementId, authorizationHeader);

        // If the announcement is present, return it with HTTP status code OK; otherwise, return 404
        return announcement.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint to update an announcement by ID
    @PutMapping("/{announcementId}")
    @IsInstructor // Custom annotation to check if the user is an instructor
    public ResponseEntity<String> updateAnnouncementById(@PathVariable Long courseId,
                                                         @PathVariable Long announcementId,
                                                         @Valid @RequestBody Announcement announcement,
                                                         @RequestHeader("Authorization") String authorizationHeader){

        // Call the service method to update the announcement
        boolean announcementUpdated = announcementService
                .updateAnnouncementById(courseId, announcementId, announcement, authorizationHeader);

        // If the announcement was updated successfully, return a success message; otherwise, return 404
        return announcementUpdated ? ResponseEntity.ok("Announcement updated Successfully") :
                ResponseEntity.notFound().build();
    }

    // Endpoint to delete an announcement by ID
    @DeleteMapping("/{announcementId}")
    @IsInstructor // Custom annotation to check if the user is an instructor
    public ResponseEntity<String> deleteAnnouncementById(@PathVariable Long courseId,
                                                         @PathVariable Long announcementId,
                                                         @RequestHeader("Authorization") String authorizationHeader){

        // Call the service method to delete the announcement
        boolean announcementDeleted = announcementService
                .deleteAnnouncementById(courseId, announcementId, authorizationHeader);

        // If the announcement was deleted successfully, return a success message; otherwise, return 404
        return announcementDeleted ? ResponseEntity.ok("Announcement deleted Successfully") :
                ResponseEntity.notFound().build();
    }

}

