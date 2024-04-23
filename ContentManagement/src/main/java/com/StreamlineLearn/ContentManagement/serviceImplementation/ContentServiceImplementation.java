package com.StreamlineLearn.ContentManagement.serviceImplementation;

import com.StreamlineLearn.ContentManagement.dto.ContentDto;
import com.StreamlineLearn.ContentManagement.jwtUtil.JwtService;
import com.StreamlineLearn.ContentManagement.model.Content;
import com.StreamlineLearn.ContentManagement.model.Course;
import com.StreamlineLearn.ContentManagement.model.Instructor;
import com.StreamlineLearn.ContentManagement.repository.ContentRepository;
import com.StreamlineLearn.ContentManagement.repository.CourseRepository;
import com.StreamlineLearn.ContentManagement.service.ContentService;
import com.StreamlineLearn.ContentManagement.service.CourseService;
import com.StreamlineLearn.ContentManagement.service.InstructorService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class ContentServiceImplementation implements ContentService {
    private final JwtService jwtService;
    private final CourseService courseService;
    private final ContentRepository contentRepository;
    private final CourseRepository courseRepository;
    private static final int TOKEN_PREFIX_LENGTH = 7;

    public ContentServiceImplementation(JwtService jwtService,
                                        CourseService courseService,
                                        ContentRepository contentRepository,
                                        CourseRepository courseRepository) {

        this.jwtService = jwtService;
        this.courseService = courseService;
        this.contentRepository = contentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void createContent(Long courseId, Content content, String authorizationHeader) {

        Course course = courseService.getCourseByCourseId(courseId);
        Long instructorId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        // Check if the course exists and if the logged-in instructor owns the course
        if (course != null && course.getInstructor().getId().equals(instructorId)) {
            content.setCourse(course);
            contentRepository.save(content);
        } else {
            // Handle the case where the course doesn't exist or the instructor doesn't own the course
            throw new RuntimeException("Instructor is not authorized to create content for this course");
        }
    }

    @Override
    public Set<Content> getContentsByCourseId(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(null);
        return course.getContents();
    }

    @Override
    public Optional<ContentDto> getContentById(Long courseId, Long contentId, String authorizationHeader) {
        String role = jwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

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
        String role = jwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

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
        String role = jwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

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

