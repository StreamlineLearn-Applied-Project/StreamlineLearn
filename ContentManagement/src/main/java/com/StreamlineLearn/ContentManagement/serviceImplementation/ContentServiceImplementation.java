package com.StreamlineLearn.ContentManagement.serviceImplementation;

import com.StreamlineLearn.ContentManagement.dto.ContentDto;
import com.StreamlineLearn.ContentManagement.exception.ContentException;
import com.StreamlineLearn.ContentManagement.model.Content;
import com.StreamlineLearn.ContentManagement.model.Course;
import com.StreamlineLearn.ContentManagement.repository.ContentRepository;
import com.StreamlineLearn.ContentManagement.repository.CourseRepository;
import com.StreamlineLearn.ContentManagement.service.ContentService;
import com.StreamlineLearn.ContentManagement.service.CourseService;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.StreamlineLearn.SharedModule.service.EnrollmentService;
import com.StreamlineLearn.SharedModule.service.JwtUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class ContentServiceImplementation implements ContentService {
    private final JwtUserService jwtUserService;
    private final CourseService courseService;
    private final ContentRepository contentRepository;
    private final EnrollmentService enrollmentService;
    private static final Logger logger = LoggerFactory.getLogger(ContentServiceImplementation.class);

    // Constructor injection for dependencies
    public ContentServiceImplementation(JwtUserService jwtUserService,
                                        CourseService courseService,
                                        ContentRepository contentRepository,
                                        EnrollmentService enrollmentService) {
        this.jwtUserService = jwtUserService;
        this.courseService = courseService;
        this.contentRepository = contentRepository;
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

    // Method to create a content
    @Override
    public Content createContent(Long courseId, Content content, String authorizationHeader) {
        try {
            // Retrieve the course by its ID
            Course course = courseService.getCourseByCourseId(courseId);

            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

            // Check if the course exists and if the logged-in instructor owns the course
            if (course != null && course.getInstructor().getId().equals(userSharedDto.getId())) {
                // Set the course to the content and save it in the repository
                content.setCourse(course);
                contentRepository.save(content);

                // Return the newly created content
                return content;
            } else {
                // Handle the case where the course doesn't exist, or the instructor doesn't own the course
                throw new RuntimeException("Instructor is not authorized to create content for this course");
            }
        } catch (Exception ex){
            // Log the error and throw a custom exception of course creation fails
            logger.error("An error occurred while creating Content", ex);
            // Rethrow the exception or throw a custom exception
            throw new ContentException("Failed to create Content: " + ex.getMessage());
        }
    }

    // Method to get contents by course ID
    @Override
    public Set<Content> getContentsByCourseId(Long courseId,String authorizationHeader) {
        try{
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);
            Course course = courseService.getCourseByCourseId(courseId);

            return (course != null && isAuthorizedAsInstructorOrStudent(userSharedDto.getRole(),
                    userSharedDto.getId(), courseId)) ?
                    course.getContents() : null;

        } catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic ContentException
            logger.error("An unexpected error occurred while getting contents", ex);
            throw new ContentException("Failed to get contents: " + ex.getMessage(), ex);
        }
    }

    // Method to get and contents by ID
    @Override
    public Optional<Content> getContentById(Long courseId, Long contentId, String authorizationHeader) {
        try {
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);
            Course course = courseService.getCourseByCourseId(courseId);

            if (course != null && isAuthorizedAsInstructorOrStudent(userSharedDto.getRole(),
                    userSharedDto.getId(), courseId)) {
                return course.getContents().stream()
                        .filter(content -> content.getId().equals(contentId))
                        .map(content -> new Content(content.getId(),
                                content.getTitle(),
                                content.getImage(),
                                content.getCourse()))
                        .findFirst();
            }
            return Optional.empty();
        } catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic ContentException
            logger.error("An unexpected error occurred while getting content by ID", ex);
            throw new ContentException("Failed to get content by ID: " + ex.getMessage(), ex);
        }
    }

    // Method to update a contents by ID
    @Override
    public boolean updateContentById(Long courseId, Long contentId, Content content, String authorizationHeader) {
        try{
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);
            Course course = courseService.getCourseByCourseId(courseId);

            if (course != null && isAuthorizedAsInstructor(userSharedDto.getRole(),
                    userSharedDto.getId(), courseId)) {
                Optional<Content> optionalContent = course.getContents().stream()
                        .filter(a -> a.getId().equals(contentId))
                        .findFirst();

                if (optionalContent.isPresent()) {
                    Content existingContent = optionalContent.get();
                    existingContent.setTitle(content.getTitle());
                    existingContent.setImage(content.getImage());
                    contentRepository.save(existingContent);
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic ContentException
            logger.error("An unexpected error occurred while updating content by ID", ex);
            throw new ContentException("Failed to update content by ID: " + ex.getMessage(), ex);
        }
    }

    // Method to delete a contents by ID
    @Override
    public boolean deleteContentById(Long courseId, Long contentId, String authorizationHeader) {
        try {
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);
            Course course = courseService.getCourseByCourseId(courseId);

            if (course != null && isAuthorizedAsInstructor(userSharedDto.getRole(), userSharedDto.getId(), courseId)) {
                Optional<Content> optionalContent = course.getContents().stream()
                        .filter(a -> a.getId().equals(contentId))
                        .findFirst();

                if (optionalContent.isPresent()) {
                    Content contentToRemove = optionalContent.get();
                    course.getContents().remove(contentToRemove);
                    courseService.saveCourse(course);
                    contentRepository.deleteById(contentId);
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            // Log any unexpected exceptions and throw a generic ContentException
            logger.error("An unexpected error occurred while deleting content by ID", ex);
            throw new ContentException("Failed to delete content by ID: " + ex.getMessage(), ex);
        }
    }


}

