package com.StreamlineLearn.AssessmentSubmissionService.serviceImplementation;

import com.StreamlineLearn.AssessmentSubmissionService.service.*;
import com.StreamlineLearn.SharedModule.dto.CourseAssessmentDto;
import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;
import com.StreamlineLearn.SharedModule.dto.EnrolledStudentDto;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerServiceImplementation implements KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerServiceImplementation.class);
    private final CourseService courseService;
    private final InstructorService instructorService;
    private final StudentService studentService;
    private final AssessmentService assessmentService;

    public KafkaConsumerServiceImplementation(CourseService courseService,
                                              InstructorService instructorService,
                                              StudentService studentService,
                                              AssessmentService assessmentService) {
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.studentService = studentService;
        this.assessmentService = assessmentService;
    }

    @Override
    @KafkaListener(topics = "course-details-topic", groupId = "assessment-Submission-group")
    public void consumeCourseDetails(CourseSharedDto courseSharedDto) throws JsonProcessingException {
        logger.info("EventConsumer:: courseEvent consumed events {}",
                new ObjectMapper().writeValueAsString(courseSharedDto));

        courseService.saveCourse(courseSharedDto);
    }

    @Override
    @KafkaListener(topics = "instructor-details-topic", groupId = "assessment-Submission-group")
    public void consumeInstructorDetails(UserSharedDto userDtoEvent) throws JsonProcessingException {
        logger.info("EventConsumer:: InstructorEvent consumed events {}",
                new ObjectMapper().writeValueAsString(userDtoEvent));

        instructorService.saveInstructor(userDtoEvent);
    }

    @Override
    @KafkaListener(topics = "enrolledStudent-details-topic", groupId = "assessment-Submission-group")
    public void consumeEnrolledStudentDetails(EnrolledStudentDto enrolledStudentDto) throws JsonProcessingException {
        logger.info("EventConsumer:: enrolledStudentDtoEvent consumed events {}",
                new ObjectMapper().writeValueAsString(enrolledStudentDto));

        studentService.enrollStudent(enrolledStudentDto);

    }

    @Override
    @KafkaListener(topics = "assessment-course-details-topic", groupId = "assessment-Submission-group")
    public void consumeCourseAssessmentDetails(CourseAssessmentDto courseAssessmentDto) throws JsonProcessingException {
        logger.info("EventConsumer:: CourseAssessmentEvent consumed events {}",
                new ObjectMapper().writeValueAsString(courseAssessmentDto));

        assessmentService.saveAssessment(courseAssessmentDto);

    }
}
