package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.UserManagement.model.Student;
import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.repository.StudentRepository;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import com.StreamlineLearn.UserManagement.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImplementation(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User userDetails){
        User setUser = new User();
        setUser.setFirstName(userDetails.getFirstName());
        setUser.setLastName(userDetails.getLastName());
        setUser.setUsername(userDetails.getUsername());
        setUser.setPassword(passwordEncoder.encode((userDetails.getPassword())));
        setUser.setRole(userDetails.getRole());

        return setUser;
    }


    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("User not Found"));
    }

    @Override
    public Optional<User> updateUser(Long id, User updateUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User userToUpdate = userOptional.get();

            // Update username if provided and validate uniqueness
            String newUsername = updateUser.getUsername();
            if (newUsername != null && !newUsername.equals(userToUpdate.getUsername())) {
                // Check if new username is unique
                if (userRepository.existsByUsername(newUsername)) {
                    throw new IllegalArgumentException("Username already exists");
                }
                userToUpdate.setUsername(newUsername);
            }

            // Update other fields if provided
            if (updateUser.getFirstName() != null) {
                userToUpdate.setFirstName(updateUser.getFirstName());
            }
            if (updateUser.getLastName() != null) {
                userToUpdate.setLastName(updateUser.getLastName());
            }
            if (updateUser.getPassword() != null && !updateUser.getPassword().isEmpty()) {
                userToUpdate.setPassword(passwordEncoder.encode(updateUser.getPassword()));
            }

            userRepository.save(userToUpdate);

            return Optional.of(userToUpdate);
        } else {
            return Optional.empty();
        }
    }


}
