package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.UserManagement.model.Student;
import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.repository.StudentRepository;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import com.StreamlineLearn.UserManagement.service.StudentService;
import com.StreamlineLearn.UserManagement.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImplementation implements StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public StudentServiceImplementation(StudentRepository studentRepository, UserService userService, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void createStudent(Student student) {

        student.setUser(userService.setUserDetails(student.getUser()));

        studentRepository.save(student);
    }

    @Override
    public List<Student> getAllUser() {
        return studentRepository.findAll();
    }

    @Override
    public Student getUserById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateStudent(Long id, Student updateStudent) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isPresent()){
            User userToUpdate = optionalStudent.get().getUser();
            userToUpdate.setFirstName(updateStudent.getUser().getFirstName());
            userToUpdate.setLastName(updateStudent.getUser().getLastName());
            userToUpdate.setUserName(updateStudent.getUser().getUserName());
            userToUpdate.setPassword(updateStudent.getUser().getPassword());

            userRepository.save(userToUpdate);

            Student student = optionalStudent.get();
//            student.setMajor(updateStudent.getMajor());

            studentRepository.save(student);

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteStudentById(Long id) {
        if(studentRepository.existsById(id)){
            studentRepository.deleteById(id);

            return true;
        }
        return false;
    }
}
