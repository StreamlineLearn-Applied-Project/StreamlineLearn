package com.StreamlineLearn.AnnouncementManagement.serviceImplementation;

import com.StreamlineLearn.AnnouncementManagement.model.Course;
import com.StreamlineLearn.AnnouncementManagement.model.Instructor;
import com.StreamlineLearn.AnnouncementManagement.repository.CourseRepository;
import com.StreamlineLearn.AnnouncementManagement.service.CourseService;
import com.StreamlineLearn.AnnouncementManagement.service.InstructorService;
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

        //  If an instructor exists, associate it with the course and save
        if (instructor != null) {
            // Create a new course object
            Course course = new Course();
            course.setId(courseDtoEvent.getId());
            course.setCourseName(courseDtoEvent.getCourseName());

            // Associate instructor with the course
            course.setInstructor(instructor);

            courseRepository.save(course);
        } else {
            // Handle a case where instructor with provided ID does not exist
            // You can throw an exception, log an error, or handle it as appropriate
            throw new IllegalArgumentException("Instructor with ID " +
                    courseDtoEvent.getInstructorId() + " not found.");
        }
    }

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public boolean isInstructorOfCourse(Long instructorId, Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(null);
        // Check if the instructor ID matches the ID of the instructor who owns the course
        return course.getInstructor().getId().equals(instructorId);
    }
}
