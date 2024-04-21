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

@Service
public class UserSignUpServiceImplementation implements UserSignUpService {
    private final StudentService studentService;
    private final AdministrativeService administrativeService;
    private final InstructorService instructorService;
    private final KafkaProducerService kafkaPublishingService;

    public UserSignUpServiceImplementation(StudentService studentService,
                                           AdministrativeService administrativeService,
                                           InstructorService instructorService,
                                           KafkaProducerService kafkaPublishingService) {

        this.studentService = studentService;
        this.administrativeService = administrativeService;
        this.instructorService = instructorService;
        this.kafkaPublishingService = kafkaPublishingService;
    }

    @Override
    public void userRegister(UserDto userRequest) {
        // Based on the role specified in the request, create the corresponding entity
        Role role = Objects.requireNonNull(userRequest.getUser().getRole());
        switch (role) {
            case STUDENT:
                Student student = userRequest.getStudent();
                if (student != null) {
                    Student newStudent = new Student();
                    newStudent.setUser(userRequest.getUser());
                    newStudent.setField(student.getField());
                    newStudent.setEducation(student.getEducation());

                    studentService.createStudent(newStudent);

                    // Create a UserSharedDto object with the necessary student details
                    UserSharedDto userSharedDto = new UserSharedDto();
                    userSharedDto.setId(newStudent.getId());
                    userSharedDto.setUserName(newStudent.getUser().getUsername());
                    userSharedDto.setRole(role.name());

                    // Publish the student details using the Kafka publishing service
                    kafkaPublishingService.publishStudentDetails(userSharedDto);

                } else {
                    throw new IllegalArgumentException("Student object is null in the request");
                }
                break;

            case INSTRUCTOR:
                Instructor instructor = userRequest.getInstructor();
                if (instructor != null) {
                    Instructor newInstructor = new Instructor();
                    newInstructor.setUser(userRequest.getUser());
                    newInstructor.setDepartment(instructor.getDepartment());
                    newInstructor.setExpertise(instructor.getExpertise());

                    instructorService.createInstructor(newInstructor);

                    // Create a UserSharedDto object with the necessary instructor details
                    UserSharedDto instructorSharedDto = new UserSharedDto();
                    instructorSharedDto.setId(newInstructor.getId());
                    instructorSharedDto.setUserName(newInstructor.getUser().getUsername());
                    instructorSharedDto.setRole(role.name());

                    // Publish the instructor details using the Kafka publishing service
                    kafkaPublishingService.publishInstructorDetails(instructorSharedDto);

                } else {
                    throw new IllegalArgumentException("Instructor object is null in the request");
                }
                break;

            case ADMINISTRATIVE:
                Administrative administrative = userRequest.getAdministrative();
                if (administrative != null) {
                    Administrative newAdministrative = new Administrative();
                    newAdministrative.setUser(userRequest.getUser());
                    newAdministrative.setPosition(administrative.getPosition());

                    administrativeService.createAdministrative(newAdministrative);

                    // Create a UserSharedDto object with the necessary administrative details
                    UserSharedDto adminSharedDto = new UserSharedDto();
                    adminSharedDto.setId(newAdministrative.getId());
                    adminSharedDto.setUserName(newAdministrative.getUser().getUsername());
                    adminSharedDto.setRole(role.name());

                    // Publish the administrative details using the Kafka publishing service
                    kafkaPublishingService.publishAdministrativeDetails(adminSharedDto);

                } else {
                    throw new IllegalArgumentException("Administrative object is null in the request");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid role in the request");
        }
    }

}
