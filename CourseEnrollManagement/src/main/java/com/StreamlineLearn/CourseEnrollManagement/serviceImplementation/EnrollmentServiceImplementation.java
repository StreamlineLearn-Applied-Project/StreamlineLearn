package com.StreamlineLearn.CourseEnrollManagement.serviceImplementation;


import com.StreamlineLearn.CourseEnrollManagement.model.Course;
import com.StreamlineLearn.CourseEnrollManagement.model.Enrollment;
import com.StreamlineLearn.CourseEnrollManagement.model.Student;
import com.StreamlineLearn.CourseEnrollManagement.repository.EnrollmentRepository;
import com.StreamlineLearn.CourseEnrollManagement.service.CourseService;
import com.StreamlineLearn.CourseEnrollManagement.service.EnrollmentService;
import com.StreamlineLearn.CourseEnrollManagement.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentServiceImplementation implements EnrollmentService {
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentServiceImplementation(StudentService studentService,
                                           CourseService courseService,
                                           EnrollmentRepository enrollmentRepository) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Boolean enrollStudent(String token, Long courseId) {
        Student student = studentService.saveOrGetStudent(token);
        Course course = courseService.getCourseByCourseId(courseId);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setPaid(true);

        // save the enrollment
        return enrollmentRepository.save(enrollment).isPaid();
    }
}
