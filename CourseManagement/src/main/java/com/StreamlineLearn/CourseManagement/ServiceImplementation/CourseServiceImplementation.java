package com.StreamlineLearn.CourseManagement.ServiceImplementation;

import com.StreamlineLearn.CourseManagement.model.Course;
import com.StreamlineLearn.CourseManagement.model.Instructor;
import com.StreamlineLearn.CourseManagement.model.Student;
import com.StreamlineLearn.CourseManagement.repository.CourseRepository;
import com.StreamlineLearn.CourseManagement.service.CourseService;
import com.StreamlineLearn.CourseManagement.service.InstructorService;
import com.StreamlineLearn.CourseManagement.service.StudentService;
import com.StreamlineLearn.CourseManagement.utility.JwtService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseServiceImplementation implements CourseService {
    private final CourseRepository courseRepository;
    private final InstructorService instructorService;
    private final StudentService studentService;
    private final JwtService jwtService;

    public CourseServiceImplementation(CourseRepository courseRepository,
                                       InstructorService instructorService,
                                       StudentService studentService,
                                       JwtService jwtService) {

        this.courseRepository = courseRepository;
        this.instructorService = instructorService;
        this.studentService = studentService;
        this.jwtService = jwtService;
    }

    @Override
    public void createCourse(Course course, String token) {

      Instructor instructor = instructorService.findInstructorByInstructorId(jwtService.extractRoleId(token));

       course.setInstructor(instructor);

        courseRepository.save(course);

    }

    @Override
    public void enrollStudent(Long courseId, String token) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Student student = studentService.findStudentByStudentId(jwtService.extractRoleId(token));

        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        // Check if the student is already enrolled in the course
        if (course.getStudents().contains(student)) {
            throw new RuntimeException("Student is already enrolled in the course");
        }
        course.setStudent(student);
        // Save the updated course
        courseRepository.save(course);

    }

    @Override
    public Course getCourseContent(Long courseId, String token) {
        Long studentId = jwtService.extractRoleId(token);

        boolean isStudentEnrolled = isStudentEnrolled(courseId, studentId);

        if (isStudentEnrolled){
            Course course = courseRepository.findById(courseId) // repeating same block of code
                    .orElseThrow(() -> new RuntimeException("Course not found"));;

                    return course;
        }

        return null;
    }

    @Override
    public boolean isStudentEnrolled(Long courseId, Long studentId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId); // repeating same block of code
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            Set<Student> students = course.getStudents();
            for (Student student : students) {
                if (student.getId().equals(studentId)) {
                    return true; // Student is enrolled
                }
            }
        }
        return false; // Student is not enrolled
    }


    @Override
    public List<Course> getAllTheCourse() {

        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateCourseById(Long id,Course course) {
        Optional<Course> courseOptional = courseRepository.findById(id); // repeating same block of code
        if(courseOptional.isPresent()){
           Course updateCourse = courseOptional.get();
           updateCourse.setTitle(course.getTitle());
           updateCourse.setDescription(course.getDescription());
           updateCourse.setPrice(course.getPrice());

           courseRepository.save(updateCourse);

           return true;
        }
        return false;
    }

    @Override
    public boolean deleteCourseById(Long id) {
        if(courseRepository.existsById(id)){
            courseRepository.deleteById(id);

            return true;
        }
        return false;
    }


}
