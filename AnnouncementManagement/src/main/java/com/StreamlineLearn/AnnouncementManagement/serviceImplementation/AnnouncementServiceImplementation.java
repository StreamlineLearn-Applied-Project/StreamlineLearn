package com.StreamlineLearn.AnnouncementManagement.serviceImplementation;

import com.StreamlineLearn.AnnouncementManagement.exception.AnnouncementException;
import com.StreamlineLearn.AnnouncementManagement.model.Announcement;
import com.StreamlineLearn.AnnouncementManagement.model.Course;
import com.StreamlineLearn.AnnouncementManagement.repository.AnnouncementRepository;
import com.StreamlineLearn.AnnouncementManagement.service.AnnouncementService;
import com.StreamlineLearn.AnnouncementManagement.service.CourseService;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.StreamlineLearn.SharedModule.service.EnrollmentService;
import com.StreamlineLearn.SharedModule.service.JwtUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.Set;

@Service
public class AnnouncementServiceImplementation implements AnnouncementService {
    // Dependencies
    private final JwtUserService jwtUserService;
    private final CourseService courseService;
    private final AnnouncementRepository announcementRepository;
    private final EnrollmentService enrollmentService;
    private static final Logger logger = LoggerFactory.getLogger(AnnouncementServiceImplementation.class);

    // Constructor injection for dependencies
    public AnnouncementServiceImplementation(JwtUserService jwtUserService,
                                             CourseService courseService,
                                             AnnouncementRepository announcementRepository,
                                             EnrollmentService enrollmentService) {
        this.jwtUserService = jwtUserService;
        this.courseService = courseService;
        this.announcementRepository = announcementRepository;
        this.enrollmentService = enrollmentService;
    }

    // Method to check if a user is authorized as an instructor or student
    private boolean isAuthorizedAsInstructorOrStudent(String role, Long roleId, Long courseId) {
        if ("INSTRUCTOR".equals(role)) {
            return courseService.isInstructorOfCourse(roleId, courseId);
        } else if ("STUDENT".equals(role)) {
            return enrollmentService.hasStudentPaidForCourse(roleId, courseId);
        }
        return false; // Any other role is not authorized
    }

    // Method to check if a user is authorized as an instructor
    private boolean isAuthorizedAsInstructor(String role, Long instructorId, Long courseId) {
        return "INSTRUCTOR".equals(role) && courseService.isInstructorOfCourse(instructorId, courseId);
    }


    // Method to create an announcement
    @Override
    public Announcement createAnnouncement(Long courseId, Announcement announcement, String authorizationHeader) {
        try {
            Course course = courseService.getCourseByCourseId(courseId);
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

            // Check if the course exists and if the logged-in instructor owns the course
            if (course != null && course.getInstructor().getId().equals(userSharedDto.getId())) {

                announcement.setCourse(course);
                announcementRepository.save(announcement);

                return announcement;
            } else {
                // Handle the case where the course doesn't exist, or the instructor doesn't own the course
                throw new AnnouncementException("Instructor is not authorized to create Announcement for this course");
            }

        } catch (RuntimeException ex) {
            // Log the error and throw a custom exception to announcement creation fails
            logger.error("An error occurred while creating announcement", ex);
            throw new AnnouncementException("Failed to create announcement: " + ex.getMessage());
        }

    }

    // Method to get announcements by course ID
    @Override
    public Set<Announcement> getAnnouncementsByCourseId(Long courseId,String authorizationHeader) {
        try{
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);
            Course course = courseService.getCourseByCourseId(courseId);

            return (course != null && isAuthorizedAsInstructorOrStudent(userSharedDto.getRole(),
                    userSharedDto.getId(), courseId)) ?
                    course.getAnnouncements() : null;

        } catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic AnnouncementException
            logger.error("An unexpected error occurred while getting announcements", ex);
            throw new AnnouncementException("Failed to get announcements: " + ex.getMessage(), ex);
        }
    }

    // Method to get an announcement by ID
    @Override
    public Optional<Announcement> getAnnouncementById(Long courseId, Long announcementId, String authorizationHeader) {
        try {
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);
            Course course = courseService.getCourseByCourseId(courseId);

            if (course != null && isAuthorizedAsInstructorOrStudent(userSharedDto.getRole(),
                    userSharedDto.getId(), courseId)) {
                return course.getAnnouncements().stream()
                        .filter(announcement -> announcement.getId().equals(announcementId))
                        .map(announcement -> new Announcement(announcement.getId(),
                                announcement.getAnnouncementTile(),
                                announcement.getAnnouncement(),
                                announcement.getCourse()))
                        .findFirst();
            }
            return Optional.empty();
        } catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic AnnouncementException
            logger.error("An unexpected error occurred while getting announcement by ID", ex);
            throw new AnnouncementException("Failed to get announcement by ID: " + ex.getMessage(), ex);
        }
    }

    // Method to update an announcement by ID
    @Override
    public boolean updateAnnouncementById(Long courseId, Long announcementId, Announcement announcement, String authorizationHeader) {
        try{
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);
            Course course = courseService.getCourseByCourseId(courseId);

            if (course != null && isAuthorizedAsInstructor(userSharedDto.getRole(),
                    userSharedDto.getId(), courseId)) {
                Optional<Announcement> optionalAnnouncement = course.getAnnouncements().stream()
                        .filter(a -> a.getId().equals(announcementId))
                        .findFirst();

                if (optionalAnnouncement.isPresent()) {
                    Announcement existingAnnouncement = optionalAnnouncement.get();
                    existingAnnouncement.setAnnouncementTile(announcement.getAnnouncementTile());
                    existingAnnouncement.setAnnouncement(announcement.getAnnouncement());
                    announcementRepository.save(existingAnnouncement);
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic AnnouncementException
            logger.error("An unexpected error occurred while updating announcement by ID", ex);
            throw new AnnouncementException("Failed to update announcement by ID: " + ex.getMessage(), ex);
        }
    }

    // Method to delete an announcement by ID
    @Override
    public boolean deleteAnnouncementById(Long courseId, Long announcementId, String authorizationHeader) {
        try {
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);
            Course course = courseService.getCourseByCourseId(courseId);

            if (course != null && isAuthorizedAsInstructor(userSharedDto.getRole(), userSharedDto.getId(), courseId)) {
                Optional<Announcement> optionalAnnouncement = course.getAnnouncements().stream()
                        .filter(a -> a.getId().equals(announcementId))
                        .findFirst();

                if (optionalAnnouncement.isPresent()) {
                    Announcement announcementToRemove = optionalAnnouncement.get();
                    course.getAnnouncements().remove(announcementToRemove);
                    courseService.saveCourse(course);
                    announcementRepository.deleteById(announcementId);
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic AnnouncementException
            logger.error("An unexpected error occurred while deleting announcement by ID", ex);
            throw new AnnouncementException("Failed to delete announcement by ID: " + ex.getMessage(), ex);
        }
    }

}


