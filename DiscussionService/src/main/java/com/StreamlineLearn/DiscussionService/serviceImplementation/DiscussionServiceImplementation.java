package com.StreamlineLearn.DiscussionService.serviceImplementation;

import com.StreamlineLearn.DiscussionService.jwtUtil.JwtService;
import com.StreamlineLearn.DiscussionService.model.Course;
import com.StreamlineLearn.DiscussionService.model.Discussion;
import com.StreamlineLearn.DiscussionService.model.Instructor;
import com.StreamlineLearn.DiscussionService.model.Student;
import com.StreamlineLearn.DiscussionService.repository.DiscussionRepository;
import com.StreamlineLearn.DiscussionService.service.CourseService;
import com.StreamlineLearn.DiscussionService.service.DiscussionService;
import com.StreamlineLearn.DiscussionService.service.InstructorService;
import com.StreamlineLearn.DiscussionService.service.StudentService;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussionServiceImplementation implements DiscussionService {
    private final JwtService jwtService;
    private final CourseService courseService;
    private final DiscussionRepository discussionRepository;
    private final StudentService studentService;
    private final InstructorService instructorService;
    private static final int TOKEN_PREFIX_LENGTH = 7;

    public DiscussionServiceImplementation(JwtService jwtService,
                                           CourseService courseService,
                                           DiscussionRepository discussionRepository,
                                           StudentService studentService,
                                           InstructorService instructorService) {
        this.jwtService = jwtService;
        this.courseService = courseService;
        this.discussionRepository = discussionRepository;
        this.studentService = studentService;

        this.instructorService = instructorService;
    }

    @Override
    public void createDiscussion(Long courseId, Discussion discussion, String authorizationHeader) {
        String role = jwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long studentId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        Student student = studentService.findStudentByStudentId(studentId);
        Course course = courseService.getCourseByCourseId(courseId);

        // Check if the user is a student who enrolled in the course
        if ("STUDENT".equals(role) && courseService.isStudentEnrolled(studentId, courseId)) {
            discussion.setCourse(course);
            discussion.setStudent(student);
            discussionRepository.save(discussion);
        }else {
            throw new RuntimeException("Only instructors of the course can create discussions");
        }
    }

    @Override
    public List<Discussion> getAllDiscussions(Long courseId, String authorizationHeader) {
        String role = jwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        // Check if the user is a student enrolled in the course or an instructor of the course
        if (("STUDENT".equals(role) && courseService.isStudentEnrolled(roleId, courseId)) ||
                ("INSTRUCTOR".equals(role) && courseService.isInstructorOfCourse(roleId, courseId))) {
            return discussionRepository.findByCourseId(courseId);
        } else {
            throw new RuntimeException("Unauthorized access to discussions");
        }
    }

    @Override
    public Discussion addThread(Long courseId, Long discussionId, String thread, String authorizationHeader) {
        String role = jwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        // Check if the user is a student enrolled in the course or an instructor of the course
        if (("STUDENT".equals(role) && courseService.isStudentEnrolled(roleId, courseId)) ||
                ("INSTRUCTOR".equals(role) && courseService.isInstructorOfCourse(roleId, courseId))) {
            Discussion discussion = discussionRepository.findById(discussionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Discussion not found"));

            if ("STUDENT".equals(role)) {
                Student student = studentService.findStudentByStudentId(roleId);

                String threadWithUserInfo = String.format("%s - %s: %s", student.getRole(), student.getUsername(), thread);
                discussion.getDiscussionThreads().add(threadWithUserInfo);

                return discussionRepository.save(discussion);
            } else {
                Instructor instructor = instructorService.findInstructorById(roleId);

                String threadWithUserInfo = String.format("%s - %s: %s", instructor.getRole(), instructor.getUsername(), thread);
                discussion.getDiscussionThreads().add(threadWithUserInfo);

                return discussionRepository.save(discussion);
            }

        } else {
            throw new RuntimeException("Unauthorized access to add thread");
        }
    }




    @Override
    public Boolean updateDiscussion(Long courseId, Long discussionId, Discussion discussion, String authorizationHeader) {
        Long studentId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));

        // Check if the user is a student
        Student student = studentService.findStudentByStudentId(studentId);
        if (student != null) {
            Discussion existingDiscussion = discussionRepository.findById(discussionId)
                    .orElseThrow(() -> new ResourceNotFoundException("Discussion not found"));

            // Ensure that the student attempting to update the discussion is the same as the one who created it
            if (existingDiscussion.getStudents().contains(student)) {
                // Update discussion
                existingDiscussion.setDiscussion(discussion.getDiscussion());
                discussionRepository.save(existingDiscussion);
                return true;
            } else {
                throw new RuntimeException("Student is not enrolled in the course associated with this discussion");
            }
        } else {
            throw new RuntimeException("Only students are authorized to update discussions");
        }
    }



    @Override
    public Boolean deleteDiscussion(Long courseId, Long discussionId, String authorizationHeader) {
        Long instructorId = jwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Course course = courseService.getCourseByCourseId(courseId);

        // Check if the course exists and if the logged-in instructor owns the course
        if (course != null && courseService.isInstructorOfCourse(instructorId, courseId)) {
            discussionRepository.deleteById(discussionId);
            return true;
        } else {
            throw new RuntimeException("Instructor is not authorized to delete this discussion");
        }
    }
}



