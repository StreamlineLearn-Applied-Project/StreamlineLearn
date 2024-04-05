package com.StreamlineLearn.AssessmentManagement.kafka;


import com.StreamlineLearn.AssessmentManagement.service.StudentService;
import com.StreamlineLearn.SharedModule.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class UserConsumer {
    private final StudentService studentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsumer.class);

    public UserConsumer(StudentService studentService) {
        this.studentService = studentService;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeInstructor(UserDto userDtoEvent){
        LOGGER.info(String.format("Order Received in Stock Service => %s",userDtoEvent.toString()));
        switch (userDtoEvent.getRole()) {
            case "student":
                studentService.saveStudent(userDtoEvent); // Assuming you have a method named saveStudent in StudentService
                break;
            default:
                throw new IllegalArgumentException("Invalid role specified in the request");
        }

    }


}
