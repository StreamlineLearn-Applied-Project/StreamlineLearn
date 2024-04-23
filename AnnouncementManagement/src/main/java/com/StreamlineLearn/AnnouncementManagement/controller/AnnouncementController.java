package com.StreamlineLearn.AnnouncementManagement.controller;

import com.StreamlineLearn.AnnouncementManagement.dto.AnnouncementDto;
import com.StreamlineLearn.AnnouncementManagement.model.Announcement;
import com.StreamlineLearn.AnnouncementManagement.service.AnnouncementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/courses/{courseId}/announcements")
@CrossOrigin(origins = "*")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping
    public ResponseEntity<String> createAnnouncement(@PathVariable Long courseId,
                                                     @RequestBody Announcement announcement,
                                                     @RequestHeader("Authorization") String authorizationHeader){

        announcementService.createAnnouncement(courseId, announcement, authorizationHeader );
        return new ResponseEntity<>("Announcement Added successfully.", HttpStatus.CREATED);
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

