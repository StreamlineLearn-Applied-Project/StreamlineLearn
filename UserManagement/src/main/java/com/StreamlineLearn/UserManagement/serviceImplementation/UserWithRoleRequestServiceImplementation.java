package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.UserManagement.model.*;
import com.StreamlineLearn.UserManagement.service.*;
import org.springframework.stereotype.Service;


@Service
public class UserWithRoleRequestServiceImplementation implements UserWithRoleRequestService {

    private final StudentService studentService;
    private final InstructorService instructorService;
    private final AdministrativeService administrativeService;

    public UserWithRoleRequestServiceImplementation(StudentService studentService, InstructorService instructorService, AdministrativeService administrativeService) {
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.administrativeService = administrativeService;
    }

    @Override
    public void createUserWithRole(UserWithRoleRequest userRequest) {

        // Based on the role specified in the request, create the corresponding entity
        switch (userRequest.getRole()) {
            case "student":
                Student student = userRequest.getStudent();
                if (student != null) {
                    Student newStudent = new Student();

                    newStudent.setUser(userRequest.getUser());
                    newStudent.setStudentId(student.getStudentId());
                    newStudent.setMajor(student.getMajor());

                    studentService.createStudent(newStudent);
                } else {
                    throw new IllegalArgumentException("Student object is null in the request");
                }
                break;

            case "instructor":
                Instructor instructor = userRequest.getInstructor();
                if (instructor != null) {
                    Instructor newInstructor = new Instructor();

                    newInstructor.setUser(userRequest.getUser());

                    newInstructor.setExpertise(instructor.getExpertise());
                    newInstructor.setDepartment(instructor.getDepartment());



                    instructorService.createInstructor(newInstructor);
                } else {
                    throw new IllegalArgumentException("Instructor object is null in the request");
                }
                break;

            case "administrative":

                Administrative administrative = userRequest.getAdministrative();
                if (administrative != null) {
                    Administrative newAdministrative = new Administrative();

                    newAdministrative.setUser(userRequest.getUser());

                    newAdministrative.setResponsibilities(administrative.getResponsibilities());

                    administrativeService.createAdministrative(newAdministrative);


                } else {
                    throw new IllegalArgumentException("Administrative object is null in the request");
                }
                break;

            default:
                throw new IllegalArgumentException("Unsupported role: " + userRequest.getRole());
        }

   }
}
