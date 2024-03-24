package com.StreamlineLearn.UserManagement.serviceImplementation;


import com.StreamlineLearn.UserManagement.model.TeachingAssistant;
import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.repository.TeachingAssistantRepository;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import com.StreamlineLearn.UserManagement.service.TeachingAssistantService;
import com.StreamlineLearn.UserManagement.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeachingAssistantServiceImplementation implements TeachingAssistantService {
    private final UserService userService;
    private final TeachingAssistantRepository teachingAssistantRepository;

    private final UserRepository userRepository;

    public TeachingAssistantServiceImplementation(UserService userService,
                                                  TeachingAssistantRepository teachingAssistantRepository,
                                                  UserRepository userRepository) {
        this.userService = userService;
        this.teachingAssistantRepository = teachingAssistantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createTeachingAssistant(TeachingAssistant newTeachingAssistant) {
        userService.createUser(newTeachingAssistant.getUser());

        teachingAssistantRepository.save(newTeachingAssistant);
    }

    @Override
    public List<TeachingAssistant> getAllTeachingAssistant() {

        return teachingAssistantRepository.findAll();
    }

    @Override
    public TeachingAssistant getTeachingAssistantById(Long id) {

        return teachingAssistantRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateTeachingAssistant(Long id, TeachingAssistant teachingAssistant) {
        Optional<TeachingAssistant> optionalTeachingAssistant = teachingAssistantRepository.findById(id);
        if(optionalTeachingAssistant.isPresent()){
            User userToUpdate = optionalTeachingAssistant.get().getUser();
            userToUpdate.setFirstName(teachingAssistant.getUser().getFirstName());
            userToUpdate.setLastName(teachingAssistant.getUser().getLastName());
            userToUpdate.setUserName(teachingAssistant.getUser().getUserName());
            userToUpdate.setPassword(teachingAssistant.getUser().getPassword());

            userRepository.save(userToUpdate);

            TeachingAssistant teachingAssistant1 = optionalTeachingAssistant.get();
            teachingAssistant1.setAdminLevel(teachingAssistant.getAdminLevel());

            teachingAssistantRepository.save(teachingAssistant1);

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteTeachingAssistantById(Long id) {
        if (teachingAssistantRepository.existsById(id)){

            teachingAssistantRepository.deleteById(id);

            return true;
        }
        return false;
    }
}
