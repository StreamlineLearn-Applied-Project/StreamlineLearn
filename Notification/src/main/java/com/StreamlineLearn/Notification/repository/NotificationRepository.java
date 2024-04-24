package com.StreamlineLearn.Notification.repository;

import com.StreamlineLearn.Notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByStudentsId(Long studentId);

    List<Notification> findByInstructorsId(Long instructorId);
}
