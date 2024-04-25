package com.StreamlineLearn.AssessmentSubmissionService.serviceImplementation;



import com.StreamlineLearn.AssessmentSubmissionService.model.Course;
import com.StreamlineLearn.AssessmentSubmissionService.model.Instructor;
import com.StreamlineLearn.AssessmentSubmissionService.repository.CourseRepository;
import com.StreamlineLearn.AssessmentSubmissionService.service.CourseService;
import com.StreamlineLearn.AssessmentSubmissionService.service.InstructorService;
import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImplementation implements CourseService {
    private final CourseRepository courseRepository;
    private final InstructorService instructorService;

    public CourseServiceImplementation(CourseRepository courseRepository,
                                       InstructorService instructorService) {
        this.courseRepository = courseRepository;
        this.instructorService = instructorService;
    }

    @Override
    public Course getCourseByCourseId(Long id) {
        return courseRepository.findById(id).orElseThrow(null);
    }

    @Override
    public void saveCourse(CourseSharedDto courseDtoEvent) {

        // Retrieve instructor by instructorId from the instructor service
        Instructor instructor = instructorService.findInstructorById(courseDtoEvent.getInstructorId());

        //  If instructor exists, associate it with the course and save
        if (instructor != null) {
            // Create a new course object
            Course course = new Course();
            course.setId(courseDtoEvent.getId());
            course.setCourseName(courseDtoEvent.getCourseName());

            // Associate instructor with the course
            course.setInstructor(instructor);

            courseRepository.save(course);
        } else {
            // Handle case where instructor with provided ID does not exist
            // You can throw an exception, log an error, or handle it as appropriate
            throw new IllegalArgumentException("Instructor with ID " +
                    courseDtoEvent.getInstructorId() + " not found.");
        }
    }

    @Override
    public boolean isStudentEnrolled(Long studentId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        // Check if the student is in the set of students enrolled in the course
        return course.getStudents().stream().anyMatch(student -> student.getId().equals(studentId));
    }

    @Override
    public boolean isInstructorOfCourse(Long instructorId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        // Check if the instructor ID matches the ID of the instructor who owns the course
        return course.getInstructor().getId().equals(instructorId);
    }
}
