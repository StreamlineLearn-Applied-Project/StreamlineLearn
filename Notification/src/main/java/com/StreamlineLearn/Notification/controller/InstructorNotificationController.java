package com.StreamlineLearn.Notification.controller;

import com.StreamlineLearn.Notification.model.Notification;
import com.StreamlineLearn.Notification.service.NotificationService;
import com.StreamlineLearn.SharedModule.annotation.IsInstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home/instructor/notifications")
public class InstructorNotificationController {

    private final NotificationService notificationService;

    public InstructorNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    @IsInstructor
    public ResponseEntity<List<Notification>> getInstructorNotifications(@RequestHeader("Authorization") String authorizationHeader) {
        List<Notification> notifications = notificationService.getInstructorNotifications(authorizationHeader);

        return ResponseEntity.ok(notifications);
    }
}



