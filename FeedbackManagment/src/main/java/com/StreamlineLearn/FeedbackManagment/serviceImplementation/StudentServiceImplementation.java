package com.StreamlineLearn.FeedbackManagment.serviceImplementation;




import com.StreamlineLearn.FeedbackManagment.model.Student;
import com.StreamlineLearn.FeedbackManagment.repository.StudentRepository;
import com.StreamlineLearn.FeedbackManagment.service.StudentService;
import com.StreamlineLearn.FeedbackManagment.utility.JwtService;
import com.StreamlineLearn.SharedModule.dto.UserDto;
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
    public Student saveStudent(UserDto userDtoEvent) {

        // Check if the student already exists
        Student existingStudent = findStudentByStudentId(userDtoEvent.getId());

        if (existingStudent != null) {
            // If student already exists, return existingStudent or handle the case accordingly
            return existingStudent;
        } else {
            // Create a new student object
            Student student = new Student();
            student.setStudentId(userDtoEvent.getId()); // Assuming you set the ID here, adjust as necessary
            student.setUserName(userDtoEvent.getUserName()); // Assuming you set the username here, adjust as necessary
            student.setRole(userDtoEvent.getRole());

            // Save the new instructor
            return studentRepository.save(student);

        }
    }
}
