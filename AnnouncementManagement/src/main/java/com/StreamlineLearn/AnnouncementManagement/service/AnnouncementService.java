package com.StreamlineLearn.AnnouncementManagement.service;


import com.StreamlineLearn.AnnouncementManagement.dto.AnnouncementDto;
import com.StreamlineLearn.AnnouncementManagement.model.Announcement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;
import java.util.Set;

public interface AnnouncementService {
    Announcement createAnnouncement(Long courseId, Announcement announcement, String authorizationHeader);

    Set<Announcement> getAnnouncementsByCourseId(Long id);

    Optional<AnnouncementDto> getAnnouncementById(Long courseId, Long announcementId, String authorizationHeader);

    boolean updateAnnouncementById(Long courseId, Long announcementId,Announcement announcement,String authorizationHeader);

    boolean deleteAnnouncementById(Long courseId, Long announcementId,String authorizationHeader);

}


