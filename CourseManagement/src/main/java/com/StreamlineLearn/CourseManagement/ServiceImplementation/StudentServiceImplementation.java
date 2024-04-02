package com.StreamlineLearn.CourseManagement.ServiceImplementation;


import com.StreamlineLearn.CourseManagement.model.Student;
import com.StreamlineLearn.CourseManagement.repository.StudentRepository;
import com.StreamlineLearn.CourseManagement.service.StudentService;
import com.StreamlineLearn.CourseManagement.utility.JwtService;
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
    public Student findStudentByInstructorId(Long studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    @Override
    public Student saveStudent(String token) {
        String username = jwtService.extractUserName(token);
        Long studentId = jwtService.extractRoleId(token);
        String role = jwtService.extractUserRole(token);

        // Check if the student already exists
        Student existingStudent = findStudentByInstructorId(studentId);

        if (existingStudent != null) {
            // If student already exists, return existingStudent or handle the case accordingly
            return existingStudent;
        } else {
            // Create a new student object
            Student student = new Student();
            student.setStudentId(studentId); // Assuming you set the ID here, adjust as necessary
            student.setUserName(username); // Assuming you set the username here, adjust as necessary
            student.setRole(role);

            // Save the new instructor
            return studentRepository.save(student);

        }
    }
}
