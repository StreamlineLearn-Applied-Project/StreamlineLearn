package com.StreamlineLearn.AssessmentManagement.serviceImplementation;


import com.StreamlineLearn.AssessmentManagement.model.Course;
import com.StreamlineLearn.AssessmentManagement.model.Instructor;
import com.StreamlineLearn.AssessmentManagement.repository.CourseRepository;
import com.StreamlineLearn.AssessmentManagement.service.CourseService;
import com.StreamlineLearn.AssessmentManagement.service.InstructorService;
import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;
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
}

