package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.UserManagement.model.Instructor;
import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.repository.InstructorRepository;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import com.StreamlineLearn.UserManagement.service.InstructorService;
import com.StreamlineLearn.UserManagement.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorServiceImplementation implements InstructorService {
    private final InstructorRepository instructorRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public InstructorServiceImplementation(InstructorRepository instructorRepository,
                                           UserService userService,
                                           UserRepository userRepository,
                                           PasswordEncoder passwordEncoder) {
        this.instructorRepository = instructorRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createInstructor(Instructor newInstructor) {
        newInstructor.setUser(userService.setUserDetails(newInstructor.getUser()));

        instructorRepository.save(newInstructor);
    }

    @Override
    public List<Instructor> getAllInstructor() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor getInstructorById(Long id) {
        return instructorRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateInstructor(Long id, Instructor updateInstructor) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        if(optionalInstructor.isPresent()){
            User userToUpdate = optionalInstructor.get().getUser();
            userToUpdate.setFirstName(updateInstructor.getUser().getFirstName());
            userToUpdate.setLastName(updateInstructor.getUser().getLastName());
            userToUpdate.setUserName(updateInstructor.getUser().getUserName());
            userToUpdate.setPassword(passwordEncoder.encode(updateInstructor.getUser().getPassword()));

            userRepository.save(userToUpdate);

            Instructor instructor = optionalInstructor.get();
            instructor.setDepartment(updateInstructor.getDepartment());
            instructor.setExpertise(updateInstructor.getExpertise());

            instructorRepository.save(instructor);

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteInstructorById(Long id) {

        if (instructorRepository.existsById(id)){

            instructorRepository.deleteById(id);

            return true;
        }
        return false;
    }
}
