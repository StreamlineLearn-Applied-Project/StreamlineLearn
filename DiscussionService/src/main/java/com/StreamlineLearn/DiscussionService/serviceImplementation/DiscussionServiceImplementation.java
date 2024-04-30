package com.StreamlineLearn.DiscussionService.serviceImplementation;

import com.StreamlineLearn.DiscussionService.exception.DiscussionException;
import com.StreamlineLearn.DiscussionService.model.Course;
import com.StreamlineLearn.DiscussionService.model.Discussion;
import com.StreamlineLearn.DiscussionService.model.Instructor;
import com.StreamlineLearn.DiscussionService.model.Student;
import com.StreamlineLearn.DiscussionService.repository.DiscussionRepository;
import com.StreamlineLearn.DiscussionService.service.CourseService;
import com.StreamlineLearn.DiscussionService.service.DiscussionService;
import com.StreamlineLearn.DiscussionService.service.InstructorService;
import com.StreamlineLearn.DiscussionService.service.StudentService;

import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.StreamlineLearn.SharedModule.service.JwtUserService;
import com.StreamlineLearn.SharedModule.sharedException.UnauthorizedException;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DiscussionServiceImplementation implements DiscussionService {
    private final CourseService courseService;
    private final DiscussionRepository discussionRepository;
    private final StudentService studentService;
    private final InstructorService instructorService;
    private final JwtUserService jwtUserService;
    private static final Logger logger = LoggerFactory.getLogger(DiscussionServiceImplementation.class);

    public DiscussionServiceImplementation(CourseService courseService,
                                           DiscussionRepository discussionRepository,
                                           StudentService studentService,
                                           InstructorService instructorService, JwtUserService jwtUserService) {
        this.jwtUserService = jwtUserService;
        this.courseService = courseService;
        this.discussionRepository = discussionRepository;
        this.studentService = studentService;

        this.instructorService = instructorService;
    }

    @Override
    public Discussion createDiscussion(Long courseId, Discussion discussion, String authorizationHeader) {
        try {
            // Extract role and ID from the authorization header
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

            // Retrieve the course by its ID
            Course course = courseService.getCourseByCourseId(courseId);

            // Check if the user is a student enrolled in the course or an instructor of the course
            if (("STUDENT".equals(userSharedDto.getRole()) && courseService.isStudentEnrolled(userSharedDto.getId(), courseId)) ||
                    ("INSTRUCTOR".equals(userSharedDto.getRole()) && courseService.isInstructorOfCourse(userSharedDto.getId(), courseId))) {
                // Set the course to the discussion
                discussion.setCourse(course);

                // Set the creator (student or instructor) based on the user's role
                if ("STUDENT".equals(userSharedDto.getRole())) {
                    Student student = studentService.findStudentByStudentId(userSharedDto.getId());
                    discussion.setStudent(student);
                } else {
                    Instructor instructor = instructorService.findInstructorById(userSharedDto.getId());
                    discussion.setInstructor(instructor);
                }

                // Save the discussion
                discussionRepository.save(discussion);

                return discussion;
            } else {
                // Handle the case where the user does not have permission to create a discussion
                throw new RuntimeException("User does not have permission to create a discussion for this course");
            }
        } catch (Exception ex) {
            // Log the error and throw a custom exception if discussion creation fails
            logger.error("Error creating discussion", ex);
            throw new DiscussionException("Unable to create discussion: " + ex.getMessage());
        }
    }

    @Override
    public List<Discussion> getAllDiscussions(Long courseId, String authorizationHeader) {
        try {// Extract role and ID from the authorization header
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

            // Check if the user is a student enrolled in the course or an instructor of the course
            if (("STUDENT".equals(userSharedDto.getRole()) && courseService.isStudentEnrolled(userSharedDto.getId(), courseId)) ||
                    ("INSTRUCTOR".equals(userSharedDto.getRole()) && courseService.isInstructorOfCourse(userSharedDto.getId(), courseId))) {
                return discussionRepository.findByCourseId(courseId);
            } else {
                throw new RuntimeException("Unauthorized access to discussions");
            }
        } catch (Exception ex) {
            logger.error("Error getting discussions", ex);
            throw new DiscussionException("Unable to get discussions: " + ex.getMessage());
        }
    }

    @Override
    public Discussion addThread(Long courseId, Long discussionId, String thread, String authorizationHeader) {
        try {        // Extract role and ID from the authorization header
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

            // Check if the user is a student enrolled in the course or an instructor of the course
            if (("STUDENT".equals(userSharedDto.getRole()) && courseService.isStudentEnrolled(userSharedDto.getId(), courseId)) ||
                    ("INSTRUCTOR".equals(userSharedDto.getRole()) && courseService.isInstructorOfCourse(userSharedDto.getId(), courseId))) {
                Discussion discussion = discussionRepository.findById(discussionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Discussion not found"));

                if ("STUDENT".equals(userSharedDto.getRole())) {
                    Student student = studentService.findStudentByStudentId(userSharedDto.getId());

                    String threadWithUserInfo = String.format("%s - %s: %s", student.getRole(), student.getUsername(), thread);
                    discussion.getDiscussionThreads().add(threadWithUserInfo);

                } else {
                    Instructor instructor = instructorService.findInstructorById(userSharedDto.getId());

                    String threadWithUserInfo = String.format("%s - %s: %s", instructor.getRole(), instructor.getUsername(), thread);
                    discussion.getDiscussionThreads().add(threadWithUserInfo);

                }
                return discussionRepository.save(discussion);

            } else {
                throw new RuntimeException("Unauthorized access to add thread");
            }
        }  catch (Exception ex) {
            logger.error("Error updating discussion", ex);
            throw new DiscussionException("Unable to update discussion: " + ex.getMessage());
        }
    }

    @Override
    public Boolean updateDiscussion(Long courseId, Long discussionId, Discussion discussion, String authorizationHeader) {
        try{  // Extract role and ID from the authorization header
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

            // Retrieve the discussion by its ID
            Discussion existingDiscussion = discussionRepository.findById(discussionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Discussion not found"));

            // Check if the user is a student or an instructor
            if (("STUDENT".equals(userSharedDto.getRole()) &&
                    existingDiscussion.getStudents()
                            .stream()
                            .anyMatch(student -> student.getId()
                                    .equals(userSharedDto.getId()))) ||
                    ("INSTRUCTOR".equals(userSharedDto.getRole()) &&
                            existingDiscussion.getCourse()
                                    .getInstructor()
                                    .getId()
                                    .equals(userSharedDto.getId()))) {
                // Update discussion
                existingDiscussion.setDiscussion(discussion.getDiscussion());
                discussionRepository.save(existingDiscussion);
                return true;
            }
        }catch (ResourceNotFoundException ex) {
        logger.error("Discussion not found", ex);
        throw new DiscussionException("Discussion not found");
        } catch (UnauthorizedException ex) {
        logger.error("Unauthorized access", ex);
        throw new UnauthorizedException("User is not authorized to update this discussion");
        } catch (Exception ex) {
        logger.error("Error updating discussion", ex);
        throw new DiscussionException("Unable to update discussion: " + ex.getMessage());
        }

        return false;
    }

    @Override
    public Boolean deleteDiscussion(Long courseId, Long discussionId, String authorizationHeader) {
        try{// Extract role and ID from the authorization header
            UserSharedDto userSharedDto = jwtUserService.extractJwtUser(authorizationHeader);

            // Retrieve the discussion by its ID
            Discussion existingDiscussion = discussionRepository.findById(discussionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Discussion not found"));

            // Check if the user is a student or an instructor
            if (("STUDENT".equals(userSharedDto.getRole()) &&
                    existingDiscussion.getStudents().
                            stream()
                            .anyMatch(student -> student.getId()
                                    .equals(userSharedDto.getId()))) ||
                    ("INSTRUCTOR".equals(userSharedDto.getRole()) &&
                            existingDiscussion.getCourse().
                                    getInstructor()
                                    .getId().equals(userSharedDto.getId()))) {
                // Delete discussion
                discussionRepository.deleteById(discussionId);
                return true;
            }
        }catch (ResourceNotFoundException ex) {
            logger.error("Discussion not found", ex);
            throw new DiscussionException("Discussion not found");
        } catch (UnauthorizedException ex) {
            logger.error("Unauthorized access", ex);
            throw new UnauthorizedException("User is not authorized to delete this discussion");
        } catch (Exception ex) {
            logger.error("Error deleting discussion", ex);
            throw new DiscussionException("Unable to delete discussion: " + ex.getMessage());
        }
        return false;
    }

}





