package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.UserManagement.model.Administrative;
import com.StreamlineLearn.UserManagement.model.Student;
import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.repository.AdministrativeRepository;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import com.StreamlineLearn.UserManagement.service.AdministrativeService;
import com.StreamlineLearn.UserManagement.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministrativeServiceImplementation implements AdministrativeService {
    private final UserService userService;
    private final AdministrativeRepository administrativeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdministrativeServiceImplementation(UserService userService,
                                               AdministrativeRepository administrativeRepository,
                                               UserRepository userRepository,
                                               PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.administrativeRepository = administrativeRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createAdministrative(Administrative newAdministrative) {

        newAdministrative.setUser(userService.createUser(newAdministrative.getUser()));

        administrativeRepository.save(newAdministrative);
    }

    @Override
    public List<Administrative> getAllAdministrative() {

        return administrativeRepository.findAll();
    }

    @Override
    public Administrative getAdministrativeById(Long id) {

        return administrativeRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateAdministrative(Long id, Administrative updateAdministrative) {
        Optional<Administrative> optionalAdministrative = administrativeRepository.findById(id);
        if(optionalAdministrative.isPresent()){
            User userToUpdate = optionalAdministrative.get().getUser();
            userToUpdate.setFirstName(updateAdministrative.getUser().getFirstName());
            userToUpdate.setLastName(updateAdministrative.getUser().getLastName());
            userToUpdate.setUsername(updateAdministrative.getUser().getUsername());
            userToUpdate.setPassword(passwordEncoder.encode(updateAdministrative.getUser().getPassword()));

            userRepository.save(userToUpdate);

            Administrative administrative = optionalAdministrative.get();
            administrative.setPosition(updateAdministrative.getPosition());

            administrativeRepository.save(administrative);

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAdministrativeById(Long id) {
        if(administrativeRepository.existsById(id)){
            administrativeRepository.deleteById(id);

            return true;
        }
        return false;
    }
}
