package com.StreamlineLearn.AnnouncementManagement.service;


import com.StreamlineLearn.AnnouncementManagement.model.Announcement;

import java.util.Set;

public interface AnnouncementService {
    void createAnnouncement(Long courseId, Announcement announcement, String authorizationHeader);

    Announcement getAnnouncementById(Long id);

    boolean updateAnnouncementById(Long id, Announcement announcement);

    boolean deleteAnnouncementById(Long id);

    Set<Announcement> getAnnouncementByCourseId(Long id);
}


