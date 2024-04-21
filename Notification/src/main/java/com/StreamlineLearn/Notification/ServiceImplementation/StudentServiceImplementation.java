package com.StreamlineLearn.Notification.ServiceImplementation;


import com.StreamlineLearn.Notification.jwtUtil.JwtService;
import com.StreamlineLearn.Notification.model.Student;
import com.StreamlineLearn.Notification.repository.StudentRepository;
import com.StreamlineLearn.Notification.service.StudentService;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
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
    public Student findStudentByStudentId(Long studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    @Override
    public Student saveStudent(UserSharedDto userDtoEvent) {

        // Check if the student already exists
        Student existingStudent = findStudentByStudentId(userDtoEvent.getId());

        if (existingStudent != null) {
            // If student already exists, return existingStudent or handle the case accordingly
            return existingStudent;
        } else {
            // Create a new student object
            Student student = new Student();
            student.setId(userDtoEvent.getId()); // Assuming you set the ID here, adjust as necessary
            student.setUserName(userDtoEvent.getUserName()); // Assuming you set the username here, adjust as necessary
            student.setRole(userDtoEvent.getRole());

            // Save the new instructor
            return studentRepository.save(student);

        }
    }
}
