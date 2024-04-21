package com.StreamlineLearn.AnnouncementManagement.serviceImplementation;

import com.StreamlineLearn.AnnouncementManagement.service.CourseService;
import com.StreamlineLearn.AnnouncementManagement.service.InstructorService;
import com.StreamlineLearn.AnnouncementManagement.service.KafkaConsumerService;
import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;
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

    public KafkaConsumerServiceImplementation(CourseService courseService,
                                              InstructorService instructorService) {
        this.courseService = courseService;
        this.instructorService = instructorService;
    }

    @Override
    @KafkaListener(topics = "course-details-topic", groupId = "announcement-management-group")
    public void consumeCourseDetails(CourseSharedDto courseSharedDto) throws JsonProcessingException {
        logger.info("EventConsumer:: courseEvent consumed events {}",
                new ObjectMapper().writeValueAsString(courseSharedDto));

        courseService.saveCourse(courseSharedDto);
    }

    @Override
    @KafkaListener(topics = "instructor-details-topic", groupId = "announcement-management-group")
    public void consumeInstructorDetails(UserSharedDto userDtoEvent) throws JsonProcessingException {
        logger.info("EventConsumer:: InstructorEvent consumed events {}",
                new ObjectMapper().writeValueAsString(userDtoEvent));

        instructorService.saveInstructor(userDtoEvent);
    }
}
