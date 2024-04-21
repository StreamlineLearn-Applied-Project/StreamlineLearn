package com.StreamlineLearn.CourseEnrollManagement.serviceImplementation;

import com.StreamlineLearn.CourseEnrollManagement.jwtUtil.JwtService;
import com.StreamlineLearn.CourseEnrollManagement.model.Student;
import com.StreamlineLearn.CourseEnrollManagement.repository.StudentRepository;
import com.StreamlineLearn.CourseEnrollManagement.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImplementation implements StudentService {
    private final StudentRepository studentRepository;
    private final JwtService jwtService;

    public StudentServiceImplementation(StudentRepository studentRepository,
                                        JwtService jwtService) {
        this.studentRepository = studentRepository;
        this.jwtService = jwtService;
    }

    @Override
    public Student findStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);

    }
    @Override
    public Student saveOrGetStudent(String token) {

        Student studentExist = findStudentById(jwtService
                .extractRoleId(token
                        .substring(7)));

        if (studentExist == null) {
            Student student = new Student();
            student.setId(jwtService.extractRoleId(token.substring(7)));
            student.setUsername(jwtService.extractUsername(token.substring(7)));
            student.setRole(jwtService.extractRole(token.substring(7)));
            studentRepository.save(student);

            return student;
        }
        else {
            return studentExist;
        }
    }
}
