package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.UserManagement.model.Student;
import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.repository.StudentRepository;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import com.StreamlineLearn.UserManagement.service.StudentService;
import com.StreamlineLearn.UserManagement.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImplementation implements StudentService {
    // StudentRepository and UserService dependencies
    private final StudentRepository studentRepository;
    private final UserService userService;
    // Logger for logging errors and other messages
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImplementation.class);

    // Constructor to inject dependencies
    public StudentServiceImplementation(StudentRepository studentRepository,
                                        UserService userService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
    }

    // Method to create a new student
    @Override
    public void createStudent(Student student) {
        try{
            // Creating a new user for the student
            student.setUser(userService.createUser(student.getUser()));
            // Saving the student to the database
            studentRepository.save(student);
        } catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while creating student: {}", e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Error occurred while creating student", e);
        }
    }

    // Method to get all students
    @Override
    public List<Student> getAllStudent() {
        try{
            // Retrieving all students from the database
            return studentRepository.findAll();
        }catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while retrieving all students: {}", e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Error occurred while retrieving all students", e);
        }
    }

    // Method to get a student by ID
    @Override
    public Student getStudentById(Long id) {
        try{
            // Retrieving the student by ID from the database
            return studentRepository.findById(id).orElse(null);
        }catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while retrieving student with id {}: {}", id, e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Error occurred while retrieving student with id " + id, e);
        }
    }

    // Method to update a student
    @Override
    @Transactional
    public boolean updateStudent(Long id, Student updateStudent) {
        try{
            Optional<Student> optionalStudent = studentRepository.findById(id);
            if (optionalStudent.isPresent()) {
                Student student = optionalStudent.get();
                student.setEducation(updateStudent.getEducation());
                student.setField(updateStudent.getField());

                studentRepository.save(student);

                return true;
            }
            return false;
        } catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while updating student with id {}: {}", id, e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Error occurred while updating student with id " + id, e);
        }
    }

    // Method to delete a student by ID
    @Override
    public boolean deleteStudentById(Long id) {
        try{
            // Checking if the student exists
            if (studentRepository.existsById(id)) {
                // Deleting the student from the database
                studentRepository.deleteById(id);
                return true;
            }
            return false;
        }catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while deleting student with id {}: {}", id, e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Error occurred while deleting student with id " + id, e);
        }
    }
}
