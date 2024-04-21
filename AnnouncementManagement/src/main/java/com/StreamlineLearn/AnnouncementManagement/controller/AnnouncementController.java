package com.StreamlineLearn.AnnouncementManagement.controller;

import com.StreamlineLearn.AnnouncementManagement.model.Announcement;
import com.StreamlineLearn.AnnouncementManagement.service.AnnouncementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Set<Announcement> announcements = announcementService.getAnnouncementByCourseId(courseId);
        if(announcements != null && !announcements.isEmpty()){
            return new ResponseEntity<>(announcements, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable Long id){
        Announcement announcement = announcementService.getAnnouncementById(id);
        if(announcement != null){
            return new ResponseEntity<>(announcement, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAnnouncementById(@PathVariable Long id, @RequestBody Announcement announcement,
                                                         @RequestHeader("Authorization") String authorizationHeader){
        boolean announcementUpdated = announcementService.updateAnnouncementById(id, announcement);
        if(announcementUpdated){
            return new ResponseEntity<>("Announcement updated Successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Announcement not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnnouncementById(@PathVariable Long id){
        boolean announcementDeleted = announcementService.deleteAnnouncementById(id);
        if(announcementDeleted){
            return new ResponseEntity<>("Announcement deleted Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Announcement not found", HttpStatus.NOT_FOUND);
        }
    }

}

