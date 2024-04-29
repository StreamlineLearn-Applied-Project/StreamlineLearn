package com.StreamlineLearn.CourseEnrollManagement.serviceImplementation;


import com.StreamlineLearn.CourseEnrollManagement.exception.EnrollmentException;
import com.StreamlineLearn.CourseEnrollManagement.model.Course;
import com.StreamlineLearn.CourseEnrollManagement.model.Enrollment;
import com.StreamlineLearn.CourseEnrollManagement.model.Student;
import com.StreamlineLearn.CourseEnrollManagement.repository.EnrollmentRepository;
import com.StreamlineLearn.CourseEnrollManagement.service.CourseService;
import com.StreamlineLearn.CourseEnrollManagement.service.EnrollmentService;
import com.StreamlineLearn.CourseEnrollManagement.service.KafkaProducerService;
import com.StreamlineLearn.CourseEnrollManagement.service.StudentService;
import com.StreamlineLearn.SharedModule.dto.EnrolledStudentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnrollmentServiceImplementation implements EnrollmentService {
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentRepository enrollmentRepository;
    private final KafkaProducerService kafkaProducerService;
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImplementation.class);

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
        try {
            // Attempt to enroll the student and handle any potential errors
            Student student = studentService.saveOrGetStudent(token);
            Course course = courseService.getCourseByCourseId(courseId);

            // Check if the course is valid
            if (course == null) {
                throw new IllegalArgumentException("Course not found");
            }

            // Create and save the enrollment
            Enrollment enrollment = new Enrollment(student, course, true);
            enrollmentRepository.save(enrollment);

            // Publish the enrollment details
            kafkaProducerService.publishEnrollStudentDetails(new EnrolledStudentDto(
                    student.getId(), student.getUsername(), student.getRole(), course.getId()));

            // Return the payment status
            return enrollment.isPaid();
        } catch (Exception ex) {
            // Log and rethrow the exception with a custom message
            logger.error("An error occurred while enrolling the student", ex);
            throw new EnrollmentException("Failed to enroll student: " + ex.getMessage());
        }
    }

    public Boolean isStudentPaid(Long studentId, Long courseId) {
        Optional<Enrollment> enrollment = enrollmentRepository.
                findByStudentIdAndCourseId(studentId, courseId);
        return enrollment.map(Enrollment::isPaid).orElse(false);
    }
}
