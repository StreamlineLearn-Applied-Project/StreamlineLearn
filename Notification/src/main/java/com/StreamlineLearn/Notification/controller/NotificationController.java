package com.StreamlineLearn.Notification.controller;

import com.StreamlineLearn.Notification.model.Notification;
import com.StreamlineLearn.Notification.model.Student;
import com.StreamlineLearn.Notification.service.InstructorService;
import com.StreamlineLearn.Notification.service.NotificationService;
import com.StreamlineLearn.Notification.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final StudentService studentService;
    private final InstructorService instructorService;

    public NotificationController(NotificationService notificationService,
                                  StudentService studentService,
                                  InstructorService instructorService) {
        this.notificationService = notificationService;
        this.studentService = studentService;
        this.instructorService = instructorService;
    }

    // Get all notifications
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    // Get notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = notificationService.getNotificationById(id);
        return notification.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new notification
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification createdNotification = notificationService.saveNotification(notification);
        return ResponseEntity.ok(createdNotification);
    }

    // Update a notification
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification notificationDetails) {
        Optional<Notification> optionalNotification = notificationService.getNotificationById(id);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setContent(notificationDetails.getContent());
            notification.setType(notificationDetails.getType());
            notification.setStatus(notificationDetails.getStatus());
            notification.setTimestamp(LocalDateTime.now());
            Notification updatedNotification = notificationService.saveNotification(notification);
            return ResponseEntity.ok(updatedNotification);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a notification
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

//    // Add a student to a notification
//    @PostMapping("/{id}/students/{studentId}")
//    public ResponseEntity<Notification> addStudentToNotification(@PathVariable Long id, @PathVariable Long studentId) {
//        Optional<Notification> optionalNotification = notificationService.getNotificationById(id);
//        Optional<Student> optionalStudent = studentService.getStudentById(studentId);
//        if (optionalNotification.isPresent() && optionalStudent.isPresent()) {
//            Notification notification = optionalNotification.get();
//            Student student = optionalStudent.get();
//            notification.getStudents().add(student);
//            Notification updatedNotification = notificationService.saveNotification(notification);
//            return ResponseEntity.ok(updatedNotification);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // Remove a student from a notification
//    @DeleteMapping("/{id}/students/{studentId}")
//    public ResponseEntity<Notification> removeStudentFromNotification(@PathVariable Long id, @PathVariable Long studentId) {
//        Optional<Notification> optionalNotification = notificationService.getNotificationById(id);
//        Optional<Student> optionalStudent = studentService.getStudentById(studentId);
//        if (optionalNotification.isPresent() && optionalStudent.isPresent()) {
//            Notification notification = optionalNotification.get();
//            Student student = optionalStudent.get();
//            notification.getStudents().remove(student);
//            Notification updatedNotification = notificationService.saveNotification(notification);
//            return ResponseEntity.ok(updatedNotification);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // Add an instructor to a notification
//    @PostMapping("/{id}/instructors/{instructorId}")
//    public ResponseEntity<Notification> addInstructorToNotification(@PathVariable Long id, @PathVariable Long instructorId) {
//        Optional<Notification> optionalNotification = notificationService.getNotificationById(id);
//        Optional<Instructor> optionalInstructor = instructorService.getInstructorById(instructorId);
//        if (optionalNotification.isPresent() && optionalInstructor.isPresent()) {
//            Notification notification = optionalNotification.get();
//            Instructor instructor = optionalInstructor.get();
//            notification.getInstructors().add(instructor);
//            Notification updatedNotification = notificationService.saveNotification(notification);
//            return ResponseEntity.ok(updatedNotification);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // Remove an instructor from a notification
//    @DeleteMapping("/{id}/instructors/{instructorId}")
//    public ResponseEntity<Notification> removeInstructorFromNotification(@PathVariable Long id, @PathVariable Long instructorId) {
//        Optional<Notification> optionalNotification = notificationService.getNotificationById(id);
//        Optional<Instructor> optionalInstructor = instructorService.getInstructorById(instructorId);
//        if (optionalNotification.isPresent() && optionalInstructor.isPresent()) {
//            Notification notification = optionalNotification.get();
//            Instructor instructor = optionalInstructor.get();
//            notification.getInstructors().remove(instructor);
//            Notification updatedNotification = notificationService.saveNotification(notification);
//            return ResponseEntity.ok(updatedNotification);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}


