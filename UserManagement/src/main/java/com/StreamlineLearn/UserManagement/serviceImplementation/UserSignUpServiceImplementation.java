package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.StreamlineLearn.UserManagement.dto.UserDto;
import com.StreamlineLearn.UserManagement.enums.Role;
import com.StreamlineLearn.UserManagement.model.Administrative;
import com.StreamlineLearn.UserManagement.model.Instructor;
import com.StreamlineLearn.UserManagement.model.Student;
import com.StreamlineLearn.UserManagement.service.*;
import org.springframework.stereotype.Service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class UserSignUpServiceImplementation implements UserSignUpService {
    // Logger for logging errors and other messages
    private static final Logger logger = LoggerFactory.getLogger(UserSignUpServiceImplementation.class);
    // Dependencies
    private final StudentService studentService;
    private final AdministrativeService administrativeService;
    private final InstructorService instructorService;
    private final KafkaProducerService kafkaPublishingService;

    // Constructor to inject dependencies
    public UserSignUpServiceImplementation(StudentService studentService,
                                           AdministrativeService administrativeService,
                                           InstructorService instructorService,
                                           KafkaProducerService kafkaPublishingService) {
        this.studentService = studentService;
        this.administrativeService = administrativeService;
        this.instructorService = instructorService;
        this.kafkaPublishingService = kafkaPublishingService;
    }

    // Method to register a user
    @Override
    public void userRegister(UserDto userRequest) {
        try {
            // Based on the role specified in the request, create the corresponding entity
            Role role = Objects.requireNonNull(userRequest.getUser().getRole());
            switch (role) {
                case STUDENT:
                    createStudent(userRequest);
                    break;
                case INSTRUCTOR:
                    createInstructor(userRequest);
                    break;
                case ADMINISTRATIVE:
                    createAdministrative(userRequest);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid role in the request");
            }
        } catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while registering user: {}", e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Error occurred while registering user", e);
        }
    }

    // Method to create a student entity and publish its details
    private void createStudent(UserDto userRequest) {
        Student student = userRequest.getStudent();
        if (student != null) {
            Student newStudent = new Student();
            newStudent.setUser(userRequest.getUser());
            newStudent.setField(student.getField());
            newStudent.setEducation(student.getEducation());
            studentService.createStudent(newStudent);
            publishUserDetails(newStudent.getId(), newStudent.getUser().getUsername(), Role.STUDENT);
        } else {
            throw new IllegalArgumentException("Student object is null in the request");
        }
    }

    // Method to create an instructor entity and publish its details
    private void createInstructor(UserDto userRequest) {
        Instructor instructor = userRequest.getInstructor();
        if (instructor != null) {
            Instructor newInstructor = new Instructor();
            newInstructor.setUser(userRequest.getUser());
            newInstructor.setDepartment(instructor.getDepartment());
            newInstructor.setExpertise(instructor.getExpertise());
            instructorService.createInstructor(newInstructor);
            publishUserDetails(newInstructor.getId(), newInstructor.getUser().getUsername(), Role.INSTRUCTOR);
        } else {
            throw new IllegalArgumentException("Instructor object is null in the request");
        }
    }

    // Method to create an administrative entity and publish its details
    private void createAdministrative(UserDto userRequest) {
        Administrative administrative = userRequest.getAdministrative();
        if (administrative != null) {
            Administrative newAdministrative = new Administrative();
            newAdministrative.setUser(userRequest.getUser());
            newAdministrative.setPosition(administrative.getPosition());
            administrativeService.createAdministrative(newAdministrative);
            publishUserDetails(newAdministrative.getId(), newAdministrative.getUser().getUsername(), Role.ADMINISTRATIVE);
        } else {
            throw new IllegalArgumentException("Administrative object is null in the request");
        }
    }

    // Method to publish user details using Kafka publishing service
    private void publishUserDetails(Long id, String userName, Role role) {
        UserSharedDto userSharedDto = new UserSharedDto(id, userName, role.name());
        try {
            switch (role) {
                case STUDENT:
                    kafkaPublishingService.publishStudentDetails(userSharedDto);
                    break;
                case INSTRUCTOR:
                    kafkaPublishingService.publishInstructorDetails(userSharedDto);
                    break;
                case ADMINISTRATIVE:
                    kafkaPublishingService.publishAdministrativeDetails(userSharedDto);
                    break;
            }
        } catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while publishing user details: {}", e.getMessage());
            // Throwing a runtime exception with
            throw new RuntimeException("Error occurred while publishing user details", e);
        }
    }
}

