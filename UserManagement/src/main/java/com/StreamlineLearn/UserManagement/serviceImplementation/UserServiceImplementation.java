package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.UserManagement.model.Student;
import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.repository.StudentRepository;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import com.StreamlineLearn.UserManagement.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImplementation(UserRepository userRepository, StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User setUserDetails(User userDetails){
        User setUser = new User();
        setUser.setFirstName(userDetails.getFirstName());
        setUser.setLastName(userDetails.getLastName());
        setUser.setUserName(userDetails.getUserName());
        setUser.setPassword(passwordEncoder.encode((userDetails.getPassword())));

        return setUser;
    }

    @Override
    public void createUser(User newUser) {

       User addedUser =  setUserDetails(newUser);

        userRepository.save(addedUser);
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
    public User updateUser(Long id, User updateUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User userToUpdate = userOptional.get();

            userToUpdate.setUserName(updateUser.getUserName());
            userToUpdate.setFirstName(updateUser.getFirstName());
            userToUpdate.setLastName(updateUser.getLastName());
            userToUpdate.setPassword(passwordEncoder.encode(updateUser.getPassword()));

            userRepository.save(userToUpdate);

            return userToUpdate;
        }
        else return null;
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
