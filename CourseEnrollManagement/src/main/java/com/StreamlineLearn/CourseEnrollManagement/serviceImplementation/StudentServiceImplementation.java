package com.StreamlineLearn.CourseEnrollManagement.serviceImplementation;

import com.StreamlineLearn.CourseEnrollManagement.model.Student;
import com.StreamlineLearn.CourseEnrollManagement.repository.StudentRepository;
import com.StreamlineLearn.CourseEnrollManagement.service.StudentService;
import com.StreamlineLearn.SharedModule.jwtUtil.SharedJwtService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImplementation implements StudentService {
    private final StudentRepository studentRepository;
    private final SharedJwtService sharedJwtService;

    public StudentServiceImplementation(StudentRepository studentRepository,
                                        SharedJwtService sharedJwtService) {
        this.studentRepository = studentRepository;
        this.sharedJwtService = sharedJwtService;
    }

    @Override
    public Student findStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);

    }
    @Override
    public Student saveOrGetStudent(String token) {

        Student studentExist = findStudentById(sharedJwtService
                .extractRoleId(token
                        .substring(7)));

        if (studentExist == null) {
            Student student = new Student();
            student.setId(sharedJwtService.extractRoleId(token.substring(7)));
            student.setUsername(sharedJwtService.extractUsername(token.substring(7)));
            student.setRole(sharedJwtService.extractRole(token.substring(7)));
            studentRepository.save(student);

            return student;
        }
        else {
            return studentExist;
        }
    }
}
