package com.StreamlineLearn.DiscussionService.serviceImplementation;

import com.StreamlineLearn.DiscussionService.jwtUtil.JwtService;
import com.StreamlineLearn.DiscussionService.model.Course;
import com.StreamlineLearn.DiscussionService.model.Discussion;
import com.StreamlineLearn.DiscussionService.model.Student;
import com.StreamlineLearn.DiscussionService.repository.CourseRepository;
import com.StreamlineLearn.DiscussionService.repository.DiscussionRepository;
import com.StreamlineLearn.DiscussionService.repository.StudentRepository;
import com.StreamlineLearn.DiscussionService.service.CourseService;
import com.StreamlineLearn.DiscussionService.service.DiscussionService;
import com.StreamlineLearn.DiscussionService.service.StudentService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussionServiceImplementation implements DiscussionService {
    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final DiscussionRepository discussionRepository;
    private final JwtService jwtService;
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public DiscussionServiceImplementation(CourseRepository courseRepository, CourseService courseService,
                                           DiscussionRepository discussionRepository,
                                           JwtService jwtService,
                                           StudentService studentService, StudentRepository studentRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
        this.discussionRepository = discussionRepository;
        this.jwtService = jwtService;
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @Override
    public void createDiscussion(Long courseId, Discussion discussion, String authorizationHeader) {

        Student student = new Student();
        student.setId(1L);
        student.setUsername("johnDarwin");
        student.setRole("STUDENT");
        studentRepository.save(student);

        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Course Title");
        course.setStudent(student);
        courseRepository.save(course);

        discussion.setCourse(course);
        discussion.setStudent(student);
        discussionRepository.save(discussion);

    }

    public List<Discussion> getAllDiscussions(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(null);
        return course.getDiscussions();
    }


    public Discussion updateDiscussion(Long courseId, Long discussionId, Discussion updatedDiscussion) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(null);
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(null);

        if (!discussion.getCourse().getId().equals(courseId)) {
            throw new IllegalArgumentException("Discussion with id " + discussionId + " does not belong to course with id " + courseId);
        }

        discussion.setTopic(updatedDiscussion.getTopic());
        // Other properties to update

        return discussionRepository.save(discussion);
    }

    public void deleteDiscussion(Long courseId, Long discussionId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(null);
        Discussion discussion = discussionRepository.findById(discussionId)
                .orElseThrow(null);

        if (!discussion.getCourse().getId().equals(courseId)) {
            throw new IllegalArgumentException("Discussion with id " + discussionId + " does not belong to course with id " + courseId);
        }

        course.getDiscussions().remove(discussion);
        courseRepository.save(course);
        discussionRepository.delete(discussion);
    }
}
