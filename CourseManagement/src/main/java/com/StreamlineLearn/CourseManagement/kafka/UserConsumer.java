package com.StreamlineLearn.CourseManagement.kafka;


import com.StreamlineLearn.CourseManagement.service.InstructorService;
import com.StreamlineLearn.CourseManagement.service.StudentService;
import com.StreamlineLearn.SharedModule.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class UserConsumer {
    private final InstructorService instructorService;
    private final StudentService studentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsumer.class);

    public UserConsumer(InstructorService instructorService,
                        StudentService studentService) {
        this.instructorService = instructorService;
        this.studentService = studentService;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeInstructor(UserDto userDtoEvent){
        LOGGER.info(String.format("User Received in Course Management Service => %s",userDtoEvent.toString()));
        switch (userDtoEvent.getRole()) {
            case "student":
                studentService.saveStudent(userDtoEvent); // Assuming you have a method named saveStudent in StudentService
                break;
            case "instructor":
                instructorService.saveInstructor(userDtoEvent); // Assuming you have a method named saveInstructor in InstructorService
                break;
            default:
                throw new IllegalArgumentException("Invalid role specified in the request");
        }

    }


}
