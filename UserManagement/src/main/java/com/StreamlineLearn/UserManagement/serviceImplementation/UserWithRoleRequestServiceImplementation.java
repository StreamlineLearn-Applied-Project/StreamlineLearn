package com.StreamlineLearn.UserManagement.serviceImplementation;


import com.StreamlineLearn.SharedModule.dto.UserDto;
import com.StreamlineLearn.UserManagement.kafka.UserProducer;
import com.StreamlineLearn.UserManagement.model.*;
import com.StreamlineLearn.UserManagement.repository.AdministrativeRepository;
import com.StreamlineLearn.UserManagement.repository.InstructorRepository;
import com.StreamlineLearn.UserManagement.repository.StudentRepository;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import com.StreamlineLearn.UserManagement.service.*;
import org.springframework.stereotype.Service;


@Service
public class UserWithRoleRequestServiceImplementation implements UserWithRoleRequestService {

    private final StudentService studentService;
    private final InstructorService instructorService;
    private final AdministrativeService administrativeService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final AdministrativeRepository administrativeRepository;
    private final UserProducer userProducer;
    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;


    public UserWithRoleRequestServiceImplementation(StudentService studentService,
                                                    InstructorService instructorService,
                                                    AdministrativeService administrativeService,
                                                    UserRepository userRepository,
                                                    StudentRepository studentRepository,
                                                    InstructorRepository instructorRepository,
                                                    AdministrativeRepository administrativeRepository,
                                                    UserProducer userProducer,
                                                    JwtService jwtService
//                                                    AuthenticationManager authenticationManager
    ) {

        this.studentService = studentService;
        this.instructorService = instructorService;
        this.administrativeService = administrativeService;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.administrativeRepository = administrativeRepository;
        this.userProducer = userProducer;
        this.jwtService = jwtService;
//        this.authenticationManager = authenticationManager;
    }

    @Override
    public void registerUserWithRole(UserWithRoleRequest userRequest) {

        // Based on the role specified in the request, create the corresponding entity
        switch (userRequest.getRole()) {
            case "student":
                Student student = userRequest.getStudent();
                if (student != null) {
                    Student newStudent = new Student();

                    newStudent.setUser(userRequest.getUser());
                    newStudent.setEducation(student.getEducation());
                    newStudent.setField(student.getField());

                    studentService.createStudent(newStudent);

                    //Kafka
                    UserDto userDto = new UserDto();
                    userDto.setId(newStudent.getId());
                    userDto.setUserName(userRequest.getUser().getUserName());
                    userDto.setRole(userRequest.getRole());
                    userProducer.sendMessage(userDto);

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

                    //Kafka
                    UserDto userDto = new UserDto();
                    userDto.setId(newInstructor.getId());
                    userDto.setUserName(userRequest.getUser().getUserName());
                    userDto.setRole(userRequest.getRole());
                    userProducer.sendMessage(userDto);


                } else {
                    throw new IllegalArgumentException("Instructor object is null in the request");
                }
                break;

            case "administrative":

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

            default:
                throw new IllegalArgumentException("Unsupported role: " + userRequest.getRole());
        }
   }

    @Override
    public String loginUserWithRole(UserWithRoleRequest userLoginWithRole) {
        User user = userLoginWithRole.getUser();
        User foundUser = userRepository.findByUserName(user.getUserName());

//        Authentication authentication = null;
//        try {
//            authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
//        } catch (AuthenticationException e) {
//            // Authentication failed
//            return null;
//        }

//        // Authentication succeeded
//        User authenticatedUser = (User) authentication.getPrincipal();

        if (foundUser != null) {

            Long userId = foundUser.getId();

            switch (userLoginWithRole.getRole()) {
                case "student":
                    Student student = studentRepository.findByUserId(userId);
                    if (student != null) {
                        return jwtService.generateToken(user, userLoginWithRole.getRole(), student.getId());
                    }
                    break;

                case "instructor":
                    Instructor instructor = instructorRepository.findByUserId(userId);
                    if (instructor != null) {
                        return jwtService.generateToken(user, userLoginWithRole.getRole(), instructor.getId());
                    }
                    break;

                case "administrative":
                    Administrative administrative = administrativeRepository.findByUserId(userId);
                    if (administrative != null) {
                        return jwtService.generateToken(user, userLoginWithRole.getRole(), administrative.getId());
                    }
                    break;

                default:
                    // Handle unknown role
                    return null; // or throw custom exception
            }
        }
        return null;
    }

}
