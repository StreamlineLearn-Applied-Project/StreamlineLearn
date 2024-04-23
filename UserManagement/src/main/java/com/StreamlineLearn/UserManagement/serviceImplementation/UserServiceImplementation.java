package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.UserManagement.model.Student;
import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.repository.StudentRepository;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import com.StreamlineLearn.UserManagement.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        return userRepository.save(userDetails);
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
    @Transactional
    public Optional<User> updateUser(Long id, User updateUser) {
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
    }

}
