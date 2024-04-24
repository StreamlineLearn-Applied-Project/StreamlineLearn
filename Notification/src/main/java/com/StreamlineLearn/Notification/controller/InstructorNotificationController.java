package com.StreamlineLearn.Notification.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/instructor/notifications")
public class InstructorNotificationController {

    private final NotificationService notificationService;

    public InstructorNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getInstructorNotifications(@AuthenticationPrincipal Jwt jwt) {
        // Extract instructor id from JWT token
        Long instructorId = Long.parseLong(jwt.getSubject());

        // Retrieve notifications for the instructor
        List<Notification> notifications = notificationService.getInstructorNotifications(instructorId);

        return ResponseEntity.ok(notifications);
    }
}



