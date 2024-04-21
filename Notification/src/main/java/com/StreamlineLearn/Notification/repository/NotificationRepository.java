package com.StreamlineLearn.Notification.repository;

import com.StreamlineLearn.Notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
