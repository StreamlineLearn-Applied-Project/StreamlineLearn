package com.StreamlineLearn.DiscussionService.serviceImplementation;

import com.StreamlineLearn.DiscussionService.model.Course;
import com.StreamlineLearn.DiscussionService.model.Discussion;
import com.StreamlineLearn.DiscussionService.model.Student;
import com.StreamlineLearn.DiscussionService.repository.CourseRepository;
import com.StreamlineLearn.DiscussionService.repository.DiscussionRepository;
import com.StreamlineLearn.DiscussionService.service.CourseService;
import com.StreamlineLearn.DiscussionService.service.DiscussionService;
import com.StreamlineLearn.DiscussionService.service.StudentService;
import com.StreamlineLearn.DiscussionService.utility.JwtService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DiscussionServiceImplementation implements DiscussionService {
    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final DiscussionRepository discussionRepository;
    private final JwtService jwtService;
    private final StudentService studentService;

    public DiscussionServiceImplementation(CourseRepository courseRepository, CourseService courseService,
                                           DiscussionRepository discussionRepository,
                                           JwtService jwtService,
                                           StudentService studentService) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
        this.discussionRepository = discussionRepository;
        this.jwtService = jwtService;
        this.studentService = studentService;
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
