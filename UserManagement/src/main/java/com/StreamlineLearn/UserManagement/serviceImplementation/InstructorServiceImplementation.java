package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.UserManagement.model.Instructor;
import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.repository.InstructorRepository;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import com.StreamlineLearn.UserManagement.service.InstructorService;
import com.StreamlineLearn.UserManagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorServiceImplementation implements InstructorService {
    // InstructorRepository and UserService dependencies
    private final InstructorRepository instructorRepository;
    private final UserService userService;
    // Logger for logging errors and other messages
    private static final Logger logger = LoggerFactory.getLogger(InstructorServiceImplementation.class);

    // Constructor to inject dependencies
    public InstructorServiceImplementation(InstructorRepository instructorRepository,
                                           UserService userService
                                           ) {
        this.instructorRepository = instructorRepository;
        this.userService = userService;
    }

    // Method to create a new instructor
    @Override
    public void createInstructor(Instructor newInstructor) {
        try{
            // Creating a new user for the instructor
            newInstructor.setUser(userService.createUser(newInstructor.getUser()));
            // Saving the instructor to the database
            instructorRepository.save(newInstructor);
        }catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while creating instructor: {}", e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Error occurred while creating instructor", e);
        }
    }

    // Method to get all instructors
    @Override
    public List<Instructor> getAllInstructor() {
        try {
            return instructorRepository.findAll();
        }catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while retrieving all instructors: {}", e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Error occurred while retrieving all instructors", e);
        }
    }

    // Method to get an instructor by ID
    @Override
    public Instructor getInstructorById(Long id) {
        try{
            // Retrieving the instructor by ID from the database
            return instructorRepository.findById(id).orElse(null);
        }catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while retrieving instructor with id {}: {}", id, e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Error occurred while retrieving instructor with id " + id, e);
        }
    }

    // Method to update an instructor
    @Override
    public boolean updateInstructor(Long id, Instructor updateInstructor) {
        try{
            // Checking if the instructor exists
            Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
            if (optionalInstructor.isPresent()) {
                // Updating the instructor's department and expertise
                Instructor instructor = optionalInstructor.get();
                instructor.setDepartment(updateInstructor.getDepartment());
                instructor.setExpertise(updateInstructor.getExpertise());
                // Saving the updated instructor to the database
                instructorRepository.save(instructor);
                return true;
            }
            return false;
        }catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while updating instructor with id {}: {}", id, e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Error occurred while updating instructor with id " + id, e);
        }
    }

    @Override
    public boolean deleteInstructorById(Long id) {
        try{
            // Checking if the instructor exists
            if (instructorRepository.existsById(id)) {
                // Deleting the instructor from the database
                instructorRepository.deleteById(id);
                return true;
            }
            return false;
        }catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while deleting instructor with id {}: {}", id, e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Error occurred while deleting instructor with id " + id, e);
        }
    }
}
