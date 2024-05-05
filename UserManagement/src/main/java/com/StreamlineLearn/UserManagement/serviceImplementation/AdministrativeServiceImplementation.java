package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.UserManagement.model.Administrative;
import com.StreamlineLearn.UserManagement.repository.AdministrativeRepository;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import com.StreamlineLearn.UserManagement.service.AdministrativeService;
import com.StreamlineLearn.UserManagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdministrativeServiceImplementation implements AdministrativeService {
    // dependencies
    private final UserService userService;
    private final AdministrativeRepository administrativeRepository;
    // Logger for logging errors and other messages
    private static final Logger logger = LoggerFactory.getLogger(AdministrativeServiceImplementation.class);

    // Constructor to inject dependencies
    public AdministrativeServiceImplementation(UserService userService,
                                               AdministrativeRepository administrativeRepository
                                               ) {
        this.userService = userService;
        this.administrativeRepository = administrativeRepository;

    }

    // Method to create a new administrative entity
    @Override
    public void createAdministrative(Administrative newAdministrative) {
        try{
            // Creating a new user for the administrative entity
            newAdministrative.setUser(userService.createUser(newAdministrative.getUser()));
            // Saving the administrative entity to the database
            administrativeRepository.save(newAdministrative);
        }catch (Exception e) {
            // log the error
            logger.error("Error occurred while creating administrative entity: {}", e.getMessage());
            // throw an exception
            throw new RuntimeException("Error occurred while creating administrative entity", e);
        }
    }

    // Method to get all administrative entities
    @Override
    public List<Administrative> getAllAdministrative() {
        try{
            // Retrieving all administrative entities from the database
            return administrativeRepository.findAll();
        } catch (Exception e) {
            // log the error
            logger.error("Error occurred while retrieving all administrative entities: {}", e.getMessage());
            // throw an exception
            throw new RuntimeException("Error occurred while retrieving all administrative entities", e);
        }
    }

    // Method to get an administrative entity by ID
    @Override
    public Administrative getAdministrativeById(Long id) {
        try{
            // Retrieving the administrative entity by ID from the database
            return administrativeRepository.findById(id).orElse(null);
        } catch (Exception e) {
            // log the error
            logger.error("Error occurred while retrieving administrative entity with id {}: {}", id, e.getMessage());
            // throw an exception
            throw new RuntimeException("Error occurred while retrieving administrative entity with id " + id, e);
        }
    }

    // Method to update an administrative entity
    @Override
    public boolean updateAdministrative(Long id, Administrative updateAdministrative) {
        try {
            // Checking if the administrative entity exists
            Optional<Administrative> optionalAdministrative = administrativeRepository.findById(id);
            if (optionalAdministrative.isPresent()) {
                // Updating the administrative entity's position
                Administrative administrative = optionalAdministrative.get();
                administrative.setPosition(updateAdministrative.getPosition());

                // Saving the updated administrative entity to the database
                administrativeRepository.save(administrative);
                return true;
            }
            return false;
        }catch (Exception e) {
            // log the error
            logger.error("Error occurred while updating administrative entity with id {}: {}", id, e.getMessage());
            // throw an exception
            throw new RuntimeException("Error occurred while updating administrative entity with id " + id, e);
        }
    }

    // Method to delete an administrative entity by ID
    @Override
    public boolean deleteAdministrativeById(Long id) {
        try {
            // Checking if the administrative entity exists
            if (administrativeRepository.existsById(id)) {
                // Deleting the administrative entity from the database
                administrativeRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            // log the error
            logger.error("Error occurred while deleting administrative entity with id {}: {}", id, e.getMessage());
            // throw an exception
            throw new RuntimeException("Error occurred while deleting administrative entity with id " + id, e);
        }
    }
}
