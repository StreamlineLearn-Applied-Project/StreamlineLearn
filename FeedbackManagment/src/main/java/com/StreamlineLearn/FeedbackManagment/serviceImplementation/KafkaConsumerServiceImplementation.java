package com.StreamlineLearn.FeedbackManagment.serviceImplementation;



import com.StreamlineLearn.FeedbackManagment.service.CourseService;
import com.StreamlineLearn.FeedbackManagment.service.InstructorService;
import com.StreamlineLearn.FeedbackManagment.service.KafkaConsumerService;
import com.StreamlineLearn.FeedbackManagment.service.StudentService;
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

    public KafkaConsumerServiceImplementation(CourseService courseService,
                                              InstructorService instructorService,
                                              StudentService studentService) {
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.studentService = studentService;
    }

    @Override
    @KafkaListener(topics = "course-details-topic", groupId = "feedback-management-group")
    public void consumeCourseDetails(CourseSharedDto courseSharedDto) throws JsonProcessingException {
        logger.info("EventConsumer:: courseEvent consumed events {}",
                new ObjectMapper().writeValueAsString(courseSharedDto));

        courseService.saveCourse(courseSharedDto);
    }

    @Override
    @KafkaListener(topics = "instructor-details-topic", groupId = "feedback-management-group")
    public void consumeInstructorDetails(UserSharedDto userDtoEvent) throws JsonProcessingException {
        logger.info("EventConsumer:: InstructorEvent consumed events {}",
                new ObjectMapper().writeValueAsString(userDtoEvent));

        instructorService.saveInstructor(userDtoEvent);
    }

    @Override
    @KafkaListener(topics = "enrolledStudent-details-topic", groupId = "feedback-management-group")
    public void consumeEnrolledStudentDetails(EnrolledStudentDto enrolledStudentDto) throws JsonProcessingException {
        logger.info("EventConsumer:: enrolledStudentDtoEvent consumed events {}",
                new ObjectMapper().writeValueAsString(enrolledStudentDto));

        studentService.enrollStudent(enrolledStudentDto);

    }
}
