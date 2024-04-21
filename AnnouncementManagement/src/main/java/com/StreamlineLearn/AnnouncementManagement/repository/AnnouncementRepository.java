package com.StreamlineLearn.AnnouncementManagement.repository;

import com.StreamlineLearn.AnnouncementManagement.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
