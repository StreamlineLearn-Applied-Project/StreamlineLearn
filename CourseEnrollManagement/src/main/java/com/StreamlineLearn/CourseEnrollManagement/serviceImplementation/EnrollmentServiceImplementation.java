package com.StreamlineLearn.CourseEnrollManagement.serviceImplementation;


import com.StreamlineLearn.CourseEnrollManagement.model.Course;
import com.StreamlineLearn.CourseEnrollManagement.model.Enrollment;
import com.StreamlineLearn.CourseEnrollManagement.model.Student;
import com.StreamlineLearn.CourseEnrollManagement.repository.EnrollmentRepository;
import com.StreamlineLearn.CourseEnrollManagement.service.CourseService;
import com.StreamlineLearn.CourseEnrollManagement.service.EnrollmentService;
import com.StreamlineLearn.CourseEnrollManagement.service.KafkaProducerService;
import com.StreamlineLearn.CourseEnrollManagement.service.StudentService;
import com.StreamlineLearn.SharedModule.dto.EnrolledStudentDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnrollmentServiceImplementation implements EnrollmentService {
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentRepository enrollmentRepository;
    private final KafkaProducerService kafkaProducerService;

    public EnrollmentServiceImplementation(StudentService studentService,
                                           CourseService courseService,
                                           EnrollmentRepository enrollmentRepository,
                                           KafkaProducerService kafkaProducerService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentRepository = enrollmentRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public Boolean enrollStudent(String token, Long courseId) {
        Student student = studentService.saveOrGetStudent(token);
        Course course = courseService.getCourseByCourseId(courseId);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setPaid(true);

        EnrolledStudentDto enrolledStudentDto = new EnrolledStudentDto();
        enrolledStudentDto.setId(enrollment.getStudent().getId());
        enrolledStudentDto.setUserName(enrollment.getStudent().getUsername());
        enrolledStudentDto.setRole(enrollment.getStudent().getRole());
        enrolledStudentDto.setCourseId(enrollment.getCourse().getId());

        kafkaProducerService.publishEnrollStudentDetails(enrolledStudentDto);

        // save the enrollment
        return enrollmentRepository.save(enrollment).isPaid();
    }

    public Boolean isStudentPaid(Long studentId, Long courseId) {
        Optional<Enrollment> enrollment = enrollmentRepository.
                findByStudentIdAndCourseId(studentId, courseId);
        return enrollment.map(Enrollment::isPaid).orElse(false);
    }
}
