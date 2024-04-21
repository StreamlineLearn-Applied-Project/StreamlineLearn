package com.StreamlineLearn.AnnouncementManagement.serviceImplementation;

import com.StreamlineLearn.AnnouncementManagement.jwtUtil.JwtService;
import com.StreamlineLearn.AnnouncementManagement.model.Announcement;
import com.StreamlineLearn.AnnouncementManagement.model.Course;
import com.StreamlineLearn.AnnouncementManagement.repository.AnnouncementRepository;
import com.StreamlineLearn.AnnouncementManagement.repository.CourseRepository;
import com.StreamlineLearn.AnnouncementManagement.service.AnnouncementService;
import com.StreamlineLearn.AnnouncementManagement.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AnnouncementServiceImplementation implements AnnouncementService {
    private final JwtService jwtService;
    private final CourseService courseService;
    private final AnnouncementRepository announcementRepository;
    private final CourseRepository courseRepository;

    public AnnouncementServiceImplementation(JwtService jwtService,
                                             CourseService courseService,
                                             AnnouncementRepository announcementRepository, CourseRepository courseRepository) {

        this.jwtService = jwtService;
        this.courseService = courseService;
        this.announcementRepository = announcementRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void createAnnouncement(Long courseId,
                                   Announcement announcement,
                                   String authorizationHeader) {

        Course course = courseService.getCourseByCourseId(courseId);

        announcement.setCourse(course);

        announcementRepository.save(announcement);

    }

    @Override
    public Announcement getAnnouncementById(Long id) {
        return null;
    }


    @Override
    public boolean updateAnnouncementById(Long id, Announcement announcement) {
        return false;
    }

    @Override
    public boolean deleteAnnouncementById(Long id) {
        return false;
    }

    @Override
    public Set<Announcement> getAnnouncementByCourseId(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(null);
        return course.getAnnouncements();
    }
}


