package com.StreamlineLearn.ContentManagement.serviceImplementation;

import com.StreamlineLearn.ContentManagement.dto.ContentDto;
import com.StreamlineLearn.ContentManagement.exception.ContentCreationException;
import com.StreamlineLearn.ContentManagement.model.Content;
import com.StreamlineLearn.ContentManagement.model.Course;
import com.StreamlineLearn.ContentManagement.repository.ContentRepository;
import com.StreamlineLearn.ContentManagement.repository.CourseRepository;
import com.StreamlineLearn.ContentManagement.service.ContentService;
import com.StreamlineLearn.ContentManagement.service.CourseService;
import com.StreamlineLearn.SharedModule.jwtUtil.SharedJwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class ContentServiceImplementation implements ContentService {
    private final SharedJwtService sharedJwtService;
    private final CourseService courseService;
    private final ContentRepository contentRepository;
    private final CourseRepository courseRepository;
    private static final int TOKEN_PREFIX_LENGTH = 7;
    private static final Logger logger = LoggerFactory.getLogger(ContentServiceImplementation.class);

    public ContentServiceImplementation(SharedJwtService sharedJwtService,
                                        CourseService courseService,
                                        ContentRepository contentRepository,
                                        CourseRepository courseRepository) {

        this.sharedJwtService = sharedJwtService;
        this.courseService = courseService;
        this.contentRepository = contentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Content createContent(Long courseId, Content content, String authorizationHeader) {

        try {
            // Retrieve the course by its ID
            Course course = courseService.getCourseByCourseId(courseId);

            // Extract the instructor's ID from the JWT token
            Long instructorId = sharedJwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

            // Check if the course exists and if the logged-in instructor owns the course
            if (course != null && course.getInstructor().getId().equals(instructorId)) {
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
            throw new ContentCreationException("Failed to create Content: " + ex.getMessage());
        }
    }

    @Override
    public Set<Content> getContentsByCourseId(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(null);
        return course.getContents();
    }

    @Override
    public Optional<ContentDto> getContentById(Long courseId, Long contentId, String authorizationHeader) {
        String role = sharedJwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = sharedJwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            // Check if the owner of the course is the instructor
            if ("INSTRUCTOR".equals(role) && Objects.equals(course.getInstructor().getId(), roleId)) {
                Optional<Content> optionalContent = course.getContents()
                        .stream()
                        .filter(content -> content.getId().equals(contentId))
                        .findFirst();
                if (optionalContent.isPresent()) {
                    Content content = optionalContent.get();
                    return Optional.of(new ContentDto(content.getTitle(), content.getImage()));
                } else {
                    return Optional.empty(); // Content not found
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
                    Optional<Content> optionalContent = course.getContents()
                            .stream()
                            .filter(content -> content.getId().equals(contentId))
                            .findFirst();
                    if (optionalContent.isPresent()) {
                        Content content = optionalContent.get();
                        return Optional.of(new ContentDto(content.getTitle(), content.getImage()));
                    } else {
                        return Optional.empty(); // Content not found
                    }
                } else {
                    return Optional.empty(); // Student has not paid for the course
                }
            }
        }

        return Optional.empty(); // Course not found, or unauthorized access
    }


    @Override
    public boolean updateContentById(Long courseId, Long contentId, Content content, String authorizationHeader) {
        String role = sharedJwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = sharedJwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            // Check if the owner of the course is the instructor
            if ("INSTRUCTOR".equals(role) && Objects.equals(course.getInstructor().getId(), roleId)) {
                Optional<Content> optionalContent = course.getContents()
                        .stream()
                        .filter(c -> c.getId().equals(contentId))
                        .findFirst();
                if (optionalContent.isPresent()) {
                    Content existingContent = optionalContent.get();
                    existingContent.setTitle(content.getTitle());
                    existingContent.setImage(content.getImage());
                    contentRepository.save(existingContent);
                    return true;
                } else {
                    // Content not found in the course
                    return false;
                }
            } else {
                // Instructor is not authorized to update content for this course
                return false;
            }
        } else {
            // Course not found
            return false;
        }
    }

    @Override
    public boolean deleteContentById(Long courseId, Long contentId, String authorizationHeader) {
        String role = sharedJwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = sharedJwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            // Check if the owner of the course is the instructor
            if ("INSTRUCTOR".equals(role) && Objects.equals(course.getInstructor().getId(), roleId)) {
                Optional<Content> optionalContent = course.getContents()
                        .stream()
                        .filter(c -> c.getId().equals(contentId))
                        .findFirst();
                if (optionalContent.isPresent()) {
                    // Remove the announcement from the course's list of announcements
                    Content contentToRemove = optionalContent.get();
                    course.getContents().remove(contentToRemove);

                    courseRepository.save(course); // Save the course to update the changes

                    contentRepository.deleteById(contentId);
                    return true;
                } else {
                    // Content not found in the course
                    return false;
                }
            } else {
                // Instructor is not authorized to delete content for this course
                return false;
            }
        } else {
            // Course not found
            return false;
        }
    }


}

