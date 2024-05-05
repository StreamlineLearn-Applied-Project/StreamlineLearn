package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import com.StreamlineLearn.UserManagement.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // Logger for logging errors and other messages
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);

    // Constructor to inject dependencies
    public UserServiceImplementation(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Method to create a new user
    @Override
    public User createUser(User userDetails){
        try{
            userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            return userRepository.save(userDetails);
        } catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while creating user: {}", e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Error occurred while creating user", e);
        }
    }

    // Method to get all users
    @Override
    public List<User> getAllUser() {
        try{
            // Retrieving the users from the database
            return userRepository.findAll();
        }catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while retrieving all users: {}", e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Error occurred while retrieving all users", e);
        }
    }

    // Method to get a user by ID
    @Override
    public User getUserById(Long id) {
        try{
            // Retrieving the user by ID from the database
            return userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("User not Found"));
        } catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while retrieving user with id {}: {}", id, e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Error occurred while retrieving user with id " + id, e);
        }
    }

    // Method to update a user
    @Override
    @Transactional
    public Optional<User> updateUser(Long id, User updateUser) {
        try{
            return Optional.ofNullable(userRepository.findById(id).map(userToUpdate -> {
                Optional.ofNullable(updateUser.getUsername())
                        .filter(username -> !username.equals(userToUpdate.getUsername()))
                        .map(userRepository::existsByUsername)
                        .filter(exists -> !exists)
                        .orElseThrow(() -> new IllegalArgumentException("Username already exists"));

                Optional.ofNullable(updateUser.getFirstName()).ifPresent(userToUpdate::setFirstName);
                Optional.ofNullable(updateUser.getLastName()).ifPresent(userToUpdate::setLastName);
                Optional.ofNullable(updateUser.getPassword())
                        .filter(password -> !password.isEmpty())
                        .map(passwordEncoder::encode)
                        .ifPresent(userToUpdate::setPassword);

                return userRepository.save(userToUpdate);
            }).orElseThrow(() -> new EntityNotFoundException("User not Found")));
        }catch (Exception e) {
            // Logging the error
            logger.error("Error occurred while updating user with id {}: {}", id, e.getMessage());
            // Throwing a runtime exception with appropriate message and cause
            throw new RuntimeException("Error occurred while updating user with id " + id, e);
        }
    }

}
