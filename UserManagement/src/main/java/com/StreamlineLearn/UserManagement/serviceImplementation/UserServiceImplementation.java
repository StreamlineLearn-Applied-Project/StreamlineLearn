package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.UserManagement.model.Student;
import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.repository.StudentRepository;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import com.StreamlineLearn.UserManagement.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public UserServiceImplementation(UserRepository userRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void createUser(User newUser) {
        userRepository.save(newUser);

    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateUser(Long id, User updateUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User userToUpdate = userOptional.get();
            userToUpdate.setFirstName(updateUser.getFirstName());
            userToUpdate.setLastName(updateUser.getLastName());
            userToUpdate.setUserName(updateUser.getUserName());
            userToUpdate.setPassword(updateUser.getPassword());

            userRepository.save(userToUpdate);

            return true;
        }
        else return false;
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();

            // Get associated student and delete them
            List<Student> students = user.getStudents();
            for (Student student : students) {
                studentRepository.deleteById(student.getId());
            }

            // Now delete the user
            userRepository.deleteById(id);

            return true;
        }
        else {
            return false;
        }
    }

}
