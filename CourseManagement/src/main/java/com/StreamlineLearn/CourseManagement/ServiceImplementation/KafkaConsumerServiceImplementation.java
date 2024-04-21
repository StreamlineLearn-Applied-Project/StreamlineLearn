package com.StreamlineLearn.CourseManagement.ServiceImplementation;

import com.StreamlineLearn.CourseManagement.service.InstructorService;
import com.StreamlineLearn.CourseManagement.service.KafkaConsumerService;
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
    private final InstructorService instructorService;

    public KafkaConsumerServiceImplementation(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @Override
    @KafkaListener(topics = "instructor-details-topic", groupId = "course-management-group")
    public void consumeInstructorDetails(UserSharedDto userDtoEvent) throws JsonProcessingException {
        logger.info("EventConsumer:: InstructorEvent consumed events {}",
                new ObjectMapper().writeValueAsString(userDtoEvent));

        instructorService.saveInstructor(userDtoEvent);
    }
}
