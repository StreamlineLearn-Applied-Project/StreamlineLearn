package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.UserManagement.dto.UserDto;
import com.StreamlineLearn.UserManagement.enums.Role;
import com.StreamlineLearn.UserManagement.model.Administrative;
import com.StreamlineLearn.UserManagement.model.Instructor;
import com.StreamlineLearn.UserManagement.model.Student;
import com.StreamlineLearn.UserManagement.service.AdministrativeService;
import com.StreamlineLearn.UserManagement.service.InstructorService;
import com.StreamlineLearn.UserManagement.service.StudentService;
import com.StreamlineLearn.UserManagement.service.UserSignUpService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserSignUpServiceImplementation implements UserSignUpService {
    private final StudentService studentService;
    private final AdministrativeService administrativeService;

    private final InstructorService instructorService;

    public UserSignUpServiceImplementation(StudentService studentService,
                                           AdministrativeService administrativeService,
                                           InstructorService instructorService) {

        this.studentService = studentService;
        this.administrativeService = administrativeService;
        this.instructorService = instructorService;

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

                } else {
                    throw new IllegalArgumentException("Student object is null in the request");
                }
                break;
            case ADMINISTRATIVE:
                Administrative administrative = userRequest.getAdministrative();
                if (administrative != null) {
                    Administrative newAdministrative = new Administrative();
                    newAdministrative.setUser(userRequest.getUser());
                    newAdministrative.setPosition(administrative.getPosition());

                    administrativeService.createAdministrative(newAdministrative);

                } else {
                    throw new IllegalArgumentException("Administrative object is null in the request");
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

                } else {
                    throw new IllegalArgumentException("Instructor object is null in the request");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid role in the request");
        }
    }
}
