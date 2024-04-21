package com.StreamlineLearn.UserManagement.serviceImplementation;

import com.StreamlineLearn.UserManagement.model.Student;
import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.repository.StudentRepository;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import com.StreamlineLearn.UserManagement.service.StudentService;
import com.StreamlineLearn.UserManagement.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImplementation implements StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImplementation(StudentRepository studentRepository,
                                        UserService userService,
                                        PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createStudent(Student student) {

       student.setUser(userService.createUser(student.getUser()));

        studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public boolean updateStudent(Long id, Student updateStudent) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            student.setEducation(updateStudent.getEducation());
            student.setField(updateStudent.getField());

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
