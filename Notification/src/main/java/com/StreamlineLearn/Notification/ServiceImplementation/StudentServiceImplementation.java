package com.StreamlineLearn.Notification.ServiceImplementation;

import com.StreamlineLearn.Notification.model.Student;
import com.StreamlineLearn.Notification.repository.StudentRepository;
import com.StreamlineLearn.Notification.service.StudentService;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImplementation implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImplementation(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found with id: " + id));
    }

    @Override
    public void saveStudent(UserSharedDto userDtoEvent) {
        Student student = new Student();

        student.setId(userDtoEvent.getId());
        student.setUserName(userDtoEvent.getUserName());
        student.setRole(userDtoEvent.getRole());

        studentRepository.save(student);
    }


}
