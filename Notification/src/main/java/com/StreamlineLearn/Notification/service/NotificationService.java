package com.StreamlineLearn.Notification.service;

import com.StreamlineLearn.Notification.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    List<Notification> getAllNotifications();
    Optional<Notification> getNotificationById(Long id);
    Notification saveNotification(Notification notification);
    void deleteNotification(Long id);
}
