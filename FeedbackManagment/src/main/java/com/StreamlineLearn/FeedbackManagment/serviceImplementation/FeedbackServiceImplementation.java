package com.StreamlineLearn.FeedbackManagment.serviceImplementation;

import com.StreamlineLearn.FeedbackManagment.jwtUtil.JwtService;
import com.StreamlineLearn.FeedbackManagment.model.Course;
import com.StreamlineLearn.FeedbackManagment.model.Feedback;
import com.StreamlineLearn.FeedbackManagment.model.Student;
import com.StreamlineLearn.FeedbackManagment.repository.CourseRepository;
import com.StreamlineLearn.FeedbackManagment.repository.FeedbackRepository;
import com.StreamlineLearn.FeedbackManagment.repository.StudentRepository;
import com.StreamlineLearn.FeedbackManagment.service.CourseService;
import com.StreamlineLearn.FeedbackManagment.service.FeedbackService;
import com.StreamlineLearn.FeedbackManagment.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImplementation implements FeedbackService {

    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final JwtService jwtService;
    private final StudentService studentService;
    private final StudentRepository studentRepository;
    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImplementation(CourseRepository courseRepository,
                                         CourseService courseService,
                                         JwtService jwtService,
                                         StudentService studentService,
                                         StudentRepository studentRepository,
                                         FeedbackRepository feedbackRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
        this.jwtService = jwtService;
        this.studentService = studentService;
        this.studentRepository = studentRepository;
        this.feedbackRepository = feedbackRepository;
    }
    @Override
    public void createFeedback(Long courseId, Feedback feedback, String authorizationHeader) {
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

        feedback.setCourse(course);
        feedback.setStudent(student);

        feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getAllFeedbacks(Long courseId) {
        return null;
    }

    @Override
    public Feedback updateFeedback(Long courseId, Long feedbackId, Feedback feedback) {
        return null;
    }

    @Override
    public void deleteFeedbck(Long courseId, Long feedbackId) {

    }
}
