package com.StreamlineLearn.DiscussionService.serviceImplementation;


import com.StreamlineLearn.DiscussionService.model.Course;
import com.StreamlineLearn.DiscussionService.model.Student;
import com.StreamlineLearn.DiscussionService.repository.CourseRepository;
import com.StreamlineLearn.DiscussionService.repository.StudentRepository;
import com.StreamlineLearn.DiscussionService.service.CourseService;
import com.StreamlineLearn.DiscussionService.service.StudentService;
import com.StreamlineLearn.SharedModule.dto.EnrolledStudentDto;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImplementation implements StudentService {
    private final StudentRepository studentRepository;
    private final CourseService courseService;
    private final CourseRepository courseRepository;

    public StudentServiceImplementation(StudentRepository studentRepository,
                                        CourseService courseService,
                                        CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseService = courseService;
        this.courseRepository = courseRepository;
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
            student.setUsername(userDtoEvent.getUserName()); // Assuming you set the username here, adjust as necessary
            student.setRole(userDtoEvent.getRole());

            // Save the new instructor
            return studentRepository.save(student);

        }
    }

    @Override
    public void enrollStudent(EnrolledStudentDto enrolledStudentDto) {

        UserSharedDto userSharedDto = new UserSharedDto();
        userSharedDto.setId(enrolledStudentDto.getId());
        userSharedDto.setUserName(enrolledStudentDto.getUserName());
        userSharedDto.setRole(enrolledStudentDto.getRole());

        Student student = saveStudent(userSharedDto);

        // Associate the student with the course if the course exists
        Course course = courseService.getCourseByCourseId(enrolledStudentDto.getCourseId());

        if (course != null) {
            // Add the student to the course's students collection
            course.getStudents().add(student);
            // Save the course to persist the association
            courseRepository.save(course);
        } else {
            throw new EntityNotFoundException("Course not found with ID: " + enrolledStudentDto.getCourseId());
        }
    }

}
