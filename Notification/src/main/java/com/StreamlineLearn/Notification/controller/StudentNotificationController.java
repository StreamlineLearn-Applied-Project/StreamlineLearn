package com.StreamlineLearn.Notification.controller;

import com.StreamlineLearn.Notification.model.Notification;
import com.StreamlineLearn.Notification.service.NotificationService;
import com.StreamlineLearn.SharedModule.annotation.IsStudent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home/student/notifications")
public class StudentNotificationController {

    private final NotificationService notificationService;

    public StudentNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    @IsStudent
    public ResponseEntity<List<Notification>> getStudentNotifications(@RequestHeader("Authorization") String authorizationHeader) {

        List<Notification> notifications = notificationService.getStudentNotifications(authorizationHeader);

        return ResponseEntity.ok(notifications);
    }

}

