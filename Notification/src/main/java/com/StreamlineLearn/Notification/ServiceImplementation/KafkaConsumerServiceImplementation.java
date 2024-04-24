package com.StreamlineLearn.Notification.ServiceImplementation;

import com.StreamlineLearn.Notification.service.InstructorService;
import com.StreamlineLearn.Notification.service.KafkaConsumerService;
import com.StreamlineLearn.Notification.service.NotificationService;
import com.StreamlineLearn.Notification.service.StudentService;
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
    private final NotificationService notificationService;
    private final InstructorService instructorService;
    private final StudentService studentService;

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerServiceImplementation.class);

    public KafkaConsumerServiceImplementation(NotificationService notificationService,
                                              InstructorService instructorService,
                                              StudentService studentService) {
        this.notificationService = notificationService;
        this.instructorService = instructorService;
        this.studentService = studentService;
    }

    @Override
    @KafkaListener(topics = "instructor-details-topic", groupId = "notification-group")
    public void consumeInstructorDetails(UserSharedDto userDtoEvent) throws JsonProcessingException {
        logger.info("EventConsumer:: InstructorEvent consumed events {}",
                new ObjectMapper().writeValueAsString(userDtoEvent));

        instructorService.saveInstructor(userDtoEvent);
    }

    @Override
    @KafkaListener(topics = "student-details-topic", groupId = "notification-group")
    public void consumeStudentDetails(UserSharedDto userDtoEvent) throws JsonProcessingException {
        logger.info("EventConsumer:: StudentDetailsEvent consumed events {}",
                new ObjectMapper().writeValueAsString(userDtoEvent));

        // Process the student details here
        // For example, you can call a method from the notification service to handle it
        studentService.saveStudent(userDtoEvent);
    }

    @Override
    @KafkaListener(topics = "course-details-topic", groupId = "notification-group")
    public void consumeCourseDetails(CourseSharedDto courseSharedDto) throws JsonProcessingException {
        logger.info("EventConsumer:: courseEvent consumed events {}",
                new ObjectMapper().writeValueAsString(courseSharedDto));

        notificationService.notifyCourseCreated(courseSharedDto);
    }


    @Override
    @KafkaListener(topics = "enrolledStudent-details-topic", groupId = "notification-group")
    public void consumeEnrolledStudentDetails(EnrolledStudentDto enrolledStudentDto) throws JsonProcessingException {
        logger.info("EventConsumer:: enrolledStudentDtoEvent consumed events {}",
                new ObjectMapper().writeValueAsString(enrolledStudentDto));

        notificationService.notifyStudentEnrolled(enrolledStudentDto);
    }
}
