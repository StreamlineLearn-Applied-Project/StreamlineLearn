package com.StreamlineLearn.AnnouncementManagement.serviceImplementation;

import com.StreamlineLearn.AnnouncementManagement.dto.AnnouncementDto;
import com.StreamlineLearn.AnnouncementManagement.jwtUtil.JwtService;
import com.StreamlineLearn.AnnouncementManagement.model.Announcement;
import com.StreamlineLearn.AnnouncementManagement.model.Course;
import com.StreamlineLearn.AnnouncementManagement.repository.AnnouncementRepository;
import com.StreamlineLearn.AnnouncementManagement.repository.CourseRepository;
import com.StreamlineLearn.AnnouncementManagement.service.AnnouncementService;
import com.StreamlineLearn.AnnouncementManagement.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class AnnouncementServiceImplementation implements AnnouncementService {
    private final JwtService jwtService;
    private final CourseService courseService;
    private final AnnouncementRepository announcementRepository;
    private final CourseRepository courseRepository;
    private static final int TOKEN_PREFIX_LENGTH = 7;

    public AnnouncementServiceImplementation(JwtService jwtService,
                                             CourseService courseService,
                                             AnnouncementRepository announcementRepository, CourseRepository courseRepository) {

        this.jwtService = jwtService;
        this.courseService = courseService;
        this.announcementRepository = announcementRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void createAnnouncement(Long courseId, Announcement announcement, String authorizationHeader) {

        Course course = courseService.getCourseByCourseId(courseId);
        Long instructorId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        // Check if the course exists and if the logged-in instructor owns the course
        if (course != null && course.getInstructor().getId().equals(instructorId)) {

            announcement.setCourse(course);
            announcementRepository.save(announcement);
        } else {
            // Handle the case where the course doesn't exist or the instructor doesn't own the course
            throw new RuntimeException("Instructor is not authorized to create Announcement for this course");
        }


    }

    @Override
    public Set<Announcement> getAnnouncementsByCourseId(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(null);
        return course.getAnnouncements();
    }

    @Override
    public Optional<AnnouncementDto> getAnnouncementById(Long courseId, Long announcementId, String authorizationHeader) {
        String role = jwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            // Check if the owner of the course is the instructor
            if ("INSTRUCTOR".equals(role) && Objects.equals(course.getInstructor().getId(), roleId)) {
                Optional<Announcement> optionalAnnouncement = course.getAnnouncements()
                        .stream()
                        .filter(announcement -> announcement.getId().equals(announcementId))
                        .findFirst();
                if (optionalAnnouncement.isPresent()) {
                    Announcement announcement = optionalAnnouncement.get();
                    return Optional.of(new AnnouncementDto(announcement.getAnnouncementTile(), announcement.getAnnouncement()));
                } else {
                    return Optional.empty(); // Announcement not found
                }
            }

            // Check if the role is student
            if ("STUDENT".equals(role)) {
                RestTemplate restTemplate = new RestTemplate();
                String enrolmentApiUrl = "http://localhost:9090/courses/" + courseId + "/enrollments/check/" + roleId;
                String apiResponse = restTemplate.getForObject(enrolmentApiUrl, String.class);
                System.out.println("API Response: " + apiResponse);

                // Assuming the API response indicates whether the student has paid for the course
                if ("PAID".equals(apiResponse)) {
                    Optional<Announcement> optionalAnnouncement = course.getAnnouncements()
                            .stream()
                            .filter(announcement -> announcement.getId().equals(announcementId))
                            .findFirst();
                    if (optionalAnnouncement.isPresent()) {
                        Announcement announcement = optionalAnnouncement.get();
                        return Optional.of(new AnnouncementDto(announcement.getAnnouncementTile(), announcement.getAnnouncement()));
                    } else {
                        return Optional.empty(); // Announcement not found
                    }
                } else {
                    return Optional.empty(); // Student has not paid for the course
                }
            }
        }

        return Optional.empty(); // Course not found, or unauthorized access
    }

    @Override
    public boolean updateAnnouncementById(Long courseId, Long announcementId, Announcement announcement, String authorizationHeader) {
        String role = jwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            // Check if the owner of the course is the instructor
            if ("INSTRUCTOR".equals(role) && Objects.equals(course.getInstructor().getId(), roleId)) {
                Optional<Announcement> optionalAnnouncement = course.getAnnouncements()
                        .stream()
                        .filter(a -> a.getId().equals(announcementId))
                        .findFirst();
                if (optionalAnnouncement.isPresent()) {
                    Announcement existingAnnouncement = optionalAnnouncement.get();
                    existingAnnouncement.setAnnouncementTile(announcement.getAnnouncementTile());
                    existingAnnouncement.setAnnouncement(announcement.getAnnouncement());
                    announcementRepository.save(existingAnnouncement);
                    return true;
                } else {
                    // Announcement not found in the course
                    return false;
                }
            } else {
                // Instructor is not authorized to update announcement for this course
                return false;
            }
        } else {
            // Course not found
            return false;
        }
    }

    @Override
    public boolean deleteAnnouncementById(Long courseId, Long announcementId, String authorizationHeader) {
        String role = jwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            if ("INSTRUCTOR".equals(role) && Objects.equals(course.getInstructor().getId(), roleId)) {
                Optional<Announcement> optionalAnnouncement = course.getAnnouncements()
                        .stream()
                        .filter(a -> a.getId().equals(announcementId))
                        .findFirst();
                if (optionalAnnouncement.isPresent()) {
                    // Remove the announcement from the course's list of announcements
                    Announcement announcementToRemove = optionalAnnouncement.get();
                    course.getAnnouncements().remove(announcementToRemove);
                    courseRepository.save(course); // Save the course to update the changes

                    // Delete the announcement
                    announcementRepository.deleteById(announcementId);
                    return true;
                } else {
                    // Announcement not found in the course
                    return false;
                }
            } else {
                // Instructor is not authorized to delete announcement for this course
                return false;
            }
        } else {
            // Course not found
            return false;
        }
    }




}


